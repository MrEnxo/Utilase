package me.mrenxo.utilase.formating

import net.kyori.adventure.text.minimessage.MiniMessage
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


