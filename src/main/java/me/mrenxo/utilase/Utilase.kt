package me.mrenxo.utilase

import io.simplyservers.simplecommand.core.cmd
import me.mrenxo.utilase.Formating.Formatter
import me.mrenxo.utilase.Formating.customColorResolver
import me.mrenxo.utilase.Formating.dashResolver
import me.mrenxo.utilase.GUI.setPlugin
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.java.JavaPlugin

class Utilase : JavaPlugin() {


    override fun onEnable() {

        setPlugin(this);

        

        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

}


