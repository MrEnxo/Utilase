package me.mrenxo.utilase.GUI

import de.tr7zw.nbtapi.NBTItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

fun item(
    type: Material = Material.BLACK_CONCRETE,
    builder: Item.() -> Unit = {}
) = Item(type).apply(builder)

class Item(
    val type: Material
) {
    val stack = ItemStack(type, 1)
    val meta get() = stack.itemMeta

    fun stack(builder: ItemStack.() -> Unit) = stack
        .apply(builder)

    fun meta(builder: ItemMeta.() -> Unit) = meta
        ?.apply(builder)
        ?.also { stack.itemMeta = it }

    fun name(value: Component) {
        val nbt = NBTItem(stack)

        val display = nbt.getOrCreateCompound("display")

        display.setString("Name", GsonComponentSerializer.gson().serialize(value))
    }


    fun lore(vararg lines: Component) {
        val nbt = NBTItem(stack)

        val display = nbt.getOrCreateCompound("display")

        val NBTlore = display.getStringList("Lore");

        lines.forEach {
            NBTlore.add(GsonComponentSerializer.gson().serialize(it))
        }

    }


    var amount
        get() = stack.amount
        set(value) {
            stack.amount = value
        }
}