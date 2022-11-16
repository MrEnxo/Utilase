package me.mrenxo.utilase.Formating

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags


class Formatter(vararg resolvers: TagResolver) {

    lateinit var formater: MiniMessage

    init {
        var tagResovler = TagResolver.builder()
            .resolver(StandardTags.color())
            .resolver(StandardTags.decorations())
        for (resolver in resolvers) {
            tagResovler = tagResovler.resolver(resolver);
        }
        formater = MiniMessage.builder().tags( tagResovler.build()).build()
    }





}


