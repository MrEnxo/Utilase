package me.mrenxo.utilase.commands.bukkit

import me.mrenxo.utilase.commands.core.PermissionGetter
import org.bukkit.command.CommandSender

/**
 * If the commandSender is op
 */
val isOp: PermissionGetter<CommandSender> = { sender, _ -> sender.isOp }



