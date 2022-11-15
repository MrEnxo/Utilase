package me.mrenxo.utilase.Commands.bukkit

import me.mrenxo.utilase.Commands.core.PermissionGetter
import org.bukkit.command.CommandSender

/**
 * If the commandSender is op
 */
val isOp: PermissionGetter<CommandSender> = { sender, _ -> sender.isOp }



