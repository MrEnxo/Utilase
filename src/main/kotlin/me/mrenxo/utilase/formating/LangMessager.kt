package me.mrenxo.utilase.formating

import me.mrenxo.utilase.data.resourceFiles
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class LangMessager(langFile: File, plugin: JavaPlugin, vararg resolvers: TagResolver) : Messager(plugin, *resolvers) {



    private val sounds: ConfigurationSection;
    private val strings: ConfigurationSection;

    init {
        resourceFiles.getFileOrResourceFile(langFile, langFile.name, plugin)
        val langYAML = YamlConfiguration.loadConfiguration(langFile)
        sounds = langYAML.getConfigurationSection("Sounds")!!
        strings = langYAML.getConfigurationSection("Strings")!!

    }

    override fun sound(sound: String, volume: Float, pitch: Float): Sound {
        val soundinfo = sounds.getConfigurationSection(sound)
            ?: return Sound.sound(Key.key("none"),Sound.Source.PLAYER,0f,0f)
        return super.sound(soundinfo.getString("name")!!, soundinfo.getDouble("volume").toFloat(), soundinfo.getDouble("pitch").toFloat())

    }

    override fun text(string: String): Component {
        return format.formater.deserialize(strings[string]!! as String)
    }




}