package me.mrenxo.utilase.Commands.bukkit

import me.mrenxo.utilase.Commands.core.SingularArg
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.*

class ArgumentOnlinePlayer(private val plugin: Plugin) : SingularArg<Player>("online player") {
    override suspend fun processArg(arg: String) = plugin.server.getPlayer(arg)
    override suspend fun autoComplete() = plugin.server.onlinePlayers.map { it.name }
}

private fun String.UUIDOrNull(): UUID? {
    return try {
        UUID.fromString(this)
    } catch (e: IllegalArgumentException) {
        null
    }
}

class ArgumentOfflinePlayer(private val plugin: Plugin) : SingularArg<OfflinePlayer>("player") {
    private val server get() = plugin.server
    override suspend fun processArg(arg: String): OfflinePlayer? {
        val uuid = arg.UUIDOrNull()
        return if (uuid == null) server.getOfflinePlayer(arg) else server.getOfflinePlayer(uuid)
    }
}
