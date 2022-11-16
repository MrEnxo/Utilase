package me.mrenxo.utilase

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections
import java.util.ListResourceBundle


object Registrator {


    fun registerListener(listener: Listener, plugin: JavaPlugin) {
        Bukkit.getServer().pluginManager.registerEvents(listener, plugin)
    }

    fun registerListenerPackage(pkg: String, plugin: JavaPlugin) {
        val reflections = Reflections(pkg);


        val subTypes: Set<Class<out Listener>> = reflections.getSubTypesOf(Listener::class.java)

        subTypes.forEach {
            registerListener(it.getConstructor().newInstance() as Listener, plugin)
        }
    }
}