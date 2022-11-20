package me.mrenxo.utilase.formating

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver


fun customColorResolver(name: String, colors: HashMap<String, TextColor>): TagResolver {
    return TagResolver.resolver(name) { args: ArgumentQueue, ctx: Context ->
        val clr = args.popOr("The <ccolor> tag requires exactly one argument, the Color").value()
        Tag.styling(colors.get(clr)!!)
    }
}

fun dashResolver(name: String,): TagResolver {
    return TagResolver.resolver(name) { _, _ -> Tag.inserting(Component.text("                            ").decorate(TextDecoration.STRIKETHROUGH)) }
}