package me.mrenxo.utilase.formating

import net.kyori.adventure.key.Key
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.title.Title
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.time.Duration

open class Messager(plugin: JavaPlugin, vararg resolvers: TagResolver) {

    val format: Formatter
    val adventure: BukkitAudiences;

    init {
        format = Formatter(*resolvers)
        adventure = BukkitAudiences.create(plugin)
    }

    fun sendText(player: Player, string: String) {
        sendText(player, text(string))
    }

    fun sendActionBar(player: Player, string: String) {
        adventure.player(player).sendActionBar(text(string))
    }

    fun sendTitle(player: Player, mainTitle: String) {
        val title = Title.title(text(mainTitle), Component.empty());

        adventure.player(player).showTitle(title)
    }

    fun sendTitle(player: Player, mainTitle: String, FadeIn: Long, FadeOut: Long) {
        val times = Title.Times.times(Duration.ofMillis(FadeIn), Duration.ofMillis(1000), Duration.ofMillis(FadeOut))
        val title = Title.title(text(mainTitle),Component.empty(), times);

        adventure.player(player).showTitle(title)
    }

    fun sendTitle(player: Player, mainTitle: String, FadeIn: Long, FadeOut: Long, duration: Long) {
        val times = Title.Times.times(Duration.ofMillis(FadeIn), Duration.ofMillis(duration), Duration.ofMillis(FadeOut))
        val title = Title.title(text(mainTitle),Component.empty(), times);

        adventure.player(player).showTitle(title)
    }

    fun sendTitle(player: Player, mainTitle: String, subTitle: String) {
        val title = Title.title(text(mainTitle),text(subTitle));

        adventure.player(player).showTitle(title)
    }

    fun sendTitle(player: Player, mainTitle: String, subTitle: String, FadeIn: Long, FadeOut: Long) {
        val times = Title.Times.times(Duration.ofMillis(FadeIn), Duration.ofMillis(1000), Duration.ofMillis(FadeOut))
        val title = Title.title(text(mainTitle),text(subTitle), times);

        adventure.player(player).showTitle(title)
    }

    fun sendTitle(player: Player, mainTitle: String,subTitle: String, FadeIn: Long, FadeOut: Long, duration: Long) {
        val times = Title.Times.times(Duration.ofMillis(FadeIn), Duration.ofMillis(duration), Duration.ofMillis(FadeOut))
        val title = Title.title(text(mainTitle),text(subTitle), times);

        adventure.player(player).showTitle(title)
    }

    fun sendText(player: Player, string: Component = Component.empty()) {

        adventure.player(player).sendMessage(string)
    }

    fun sendText(player: CommandSender, string: Component = Component.empty()) {
        if (player is Player) {
            adventure.player(player).sendMessage(string)
        } else{
            adventure.console().sendMessage(string)
        }
    }

    fun sendText(player: CommandSender, string: String) {
        sendText(player, text(string))
    }

    fun sendActionBar(player: Player, string: Component = Component.empty()) {

        adventure.player(player).sendActionBar(string)
    }

    fun sendTitle(player: Player, mainTitle: Component = Component.empty()) {

        val title = Title.title(mainTitle,Component.empty());

        adventure.player(player).showTitle(title)
    }

    fun sendTitle(player: Player, mainTitle: Component = Component.empty(), FadeIn: Long, FadeOut: Long) {

        val times = Title.Times.times(Duration.ofMillis(FadeIn), Duration.ofMillis(1000), Duration.ofMillis(FadeOut))
        val title = Title.title(mainTitle,Component.empty(), times);

        adventure.player(player).showTitle(title)
    }

    fun sendTitle(player: Player, mainTitle: Component = Component.empty(), FadeIn: Long, FadeOut: Long, duration: Long) {

        val times = Title.Times.times(Duration.ofMillis(FadeIn), Duration.ofMillis(duration), Duration.ofMillis(FadeOut))
        val title = Title.title(mainTitle,Component.empty(), times);

        adventure.player(player).showTitle(title)
    }

    fun sendTitle(player: Player, mainTitle: Component = Component.empty(), subTitle: Component = Component.empty()) {

        val title = Title.title(mainTitle,subTitle);

        adventure.player(player).showTitle(title)
    }

    fun sendTitle(player: Player, mainTitle: Component = Component.empty(), subTitle: Component = Component.empty(), FadeIn: Long, FadeOut: Long) {

        val times = Title.Times.times(Duration.ofMillis(FadeIn), Duration.ofMillis(1000), Duration.ofMillis(FadeOut))
        val title = Title.title(mainTitle,subTitle, times);

        adventure.player(player).showTitle(title)
    }

    fun sendTitle(player: Player, mainTitle: Component = Component.empty(),subTitle: Component = Component.empty(), FadeIn: Long, FadeOut: Long, duration: Long) {

        val times = Title.Times.times(Duration.ofMillis(FadeIn), Duration.ofMillis(duration), Duration.ofMillis(FadeOut))
        val title = Title.title(mainTitle,subTitle, times);

        adventure.player(player).showTitle(title)
    }

    fun playSound(player: Player, sound: String, volume: Float, pitch: Float) {
        adventure.player(player).playSound(sound(sound, volume, pitch))
    }

    fun playSound(player: Player, sound: String, pitch: Float) {

        adventure.player(player).playSound(sound(sound, 1f, pitch))

    }

    fun playSound(player: Player, sound: String) {

        adventure.player(player).playSound(sound(sound, 1f, 1f))
    }

    open fun sound(sound: String, volume: Float, pitch: Float): Sound {
        return Sound.sound(Key.key(sound), Sound.Source.PLAYER, 1f, pitch)
    }

    open fun text(string: String): Component {
        return format.formater.deserialize(string)
    }
}