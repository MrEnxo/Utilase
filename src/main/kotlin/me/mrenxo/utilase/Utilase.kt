package me.mrenxo.utilase

import me.mrenxo.utilase.gui.setPlugin
import org.bukkit.plugin.java.JavaPlugin

open class Utilase : JavaPlugin() {


    override fun onEnable() {

        setPlugin(this);

        

        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

}


