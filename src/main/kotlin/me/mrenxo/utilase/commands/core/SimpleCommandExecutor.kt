package me.mrenxo.utilase.commands.core

import java.util.*


suspend fun <S> commandExecutor(baseNode: FunctionNode<S>, executeSender: S, permissions: Sequence<String>, args: Array<String>) {
    val argsMap = HashMap<String, Any>()

    suspend fun matchingNode(nodeOn: Node<S>, currentArgs: List<String>) {

        if (nodeOn is FunctionNode) {
            val permissionGetter = nodeOn.permission
            if (permissionGetter != null) {
                val pg = permissionGetter(executeSender, permissions)
                if (!pg) throw PermissionException()
            }
        }

        if (nodeOn.children.isNotEmpty() && currentArgs.isNotEmpty()) {
            val firstArg = currentArgs.first()

            loop@ for (node in nodeOn.children) {
                when (node) {
                    is ArgumentPreNode -> {
                        argumentLoop@ for (argumentNode in node.children) {
                            when (argumentNode) {
                                is ArgumentNode<S, *> -> {
                                    val argumentType = argumentNode.argumentType as ArgumentType<out Any>
                                    val result = argumentType.process(currentArgs)
                                    when (result) {
                                        is ProcessResult.Failure -> continue@argumentLoop
                                        is ProcessResult.Success -> {
                                            argsMap[argumentNode.referenceName] = result.result
                                            matchingNode(argumentNode, result.argsNext)
                                            return // avoid end execution
                                        }
                                    }
                                }

                                else -> {}
                            }
                        }
                    }
                    is FunctionNode -> {
                        val matches = node.matches(firstArg)
                        if (!matches) continue@loop
                        val nextArgs = currentArgs.slice(1 until currentArgs.size)
                        matchingNode(node, nextArgs)
                        return // avoid end execution
                    }

                    else -> {}
                }
            }
        }
        when (nodeOn) {
            is BaseNode -> {
                if (nodeOn.executions.isEmpty()) { // We want to tell the player what they can do
                    throw CommandSyntaxException(nodeOn)
                } else {
                    argsMap["additionalArgs"] = currentArgs.toList()
                    argsMap["additionalSentence"] = currentArgs.toList().joinToString(separator = " ")
                    nodeOn.executions.forEach { it(executeSender, nodeOn, argsMap) }
                }
            }
            else -> throw IllegalStateException("should never be on a non-base node")
        }


    }

    matchingNode(baseNode, args.toList())

}



class PermissionException : Throwable("A command was executed permission to do that command")

interface Formatter {
    fun generateHelpMessage(commandSyntaxException: CommandSyntaxException): String
}

data class CommandSyntaxException(val node: BaseNode<*>) : Throwable()

class DefaultFormatter<S>(private val user: S, private val permissible: Permissible) : Formatter {

    private fun FunctionNode<*>.helpMessageString(builder: StringBuilder) {
        val equivalent = sequenceOf(name, *(aliases)).toList()
        if (equivalent.size == 1) builder.append("\t${equivalent.first()}")
        else {
            val names = equivalent.joinToString(separator = ",")
            builder.append("\t[$names]")
        }
        if (description != null) builder.append(" - $description")
        builder.appendln()
    }

    private fun ArgumentPreNode<*>.helpMessageString(builder: StringBuilder) {
        builder.append("\t<$referenceName>")
        if (description != null) builder.append(" - $description")
        builder.appendln()

        children
            .asSequence()
            .filterIsInstance<ArgumentNode<*, *>>()
            .forEach { node ->
                val argumentType = node.argumentType
                builder.append("\t\t:${argumentType.name}")
                if (node.description != null) builder.append(" - ${node.description}")
                builder.appendln()
            }
    }

    override fun generateHelpMessage(commandSyntaxException: CommandSyntaxException): String {
        val node = commandSyntaxException.node as BaseNode<S>
        val subCommands = LinkedList<FunctionNode<*>>()
        val argumentPreNodes = LinkedList<ArgumentPreNode<*>>()

        node.children
            .forEach {
                when (it) {
                    is FunctionNode<S> -> {
                        val permission = it.permission
                        val hasPerm = if (permission != null) permission(user, permissible) else true
                        if (hasPerm) subCommands.add(it)
                    }
                    is ArgumentPreNode<*> -> argumentPreNodes.add(it)
                    else -> {}
                }
            }

        return buildString {
            if (node.children.isEmpty()) append("You do not have any other options. Report this to an admin")
            if (subCommands.isNotEmpty()) {
                appendln("Subcommands")
                subCommands.forEach {
                    it.helpMessageString(this)
                }
            }
            argumentPreNodes.forEach {
                appendln("Arguments")
                it.helpMessageString(this)
            }
        }
    }

}

fun String.toArgs() = trim().split(" ").toTypedArray()

typealias Permissible = Sequence<String>
