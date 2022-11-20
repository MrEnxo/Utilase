package me.mrenxo.utilase.gui

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


fun item(
    stack: ItemStack,
    builder: Item.() -> Unit = {}
) = Item(stack).apply(builder)



class Item {

    var stack: ItemStack;

    constructor(type: Material) {
        this.stack = ItemStack(type, 1)
    }

    constructor(stack: ItemStack) {
        this.stack = stack
    }
    val meta get() = stack.itemMeta

    fun stack(builder: ItemStack.() -> Unit) = stack
        .apply(builder)


    fun nbt(builder: NBTItem.() -> Unit) = NBTItem(stack).apply(builder).item

    val nbt get() = NBTItem(stack)

    fun meta(builder: ItemMeta.() -> Unit) = meta
        ?.apply(builder)
        ?.also { stack.itemMeta = it }

    fun meta() = meta

    fun name(value: Component): Item {
        val nbt = NBTItem(stack)

        val display = nbt.getOrCreateCompound("display")

        display.setString("Name", GsonComponentSerializer.gson().serialize(value))

        stack = nbt.item

        return this
    }

    fun lore(lines: ArrayList<Component>): Item {
        val nbt = NBTItem(stack)

        val display = nbt.getOrCreateCompound("display")

        val NBTlore = display.getStringList("Lore");

        lines.forEach {
            NBTlore.add(GsonComponentSerializer.gson().serialize(it))
        }

        stack = nbt.item

        return this
    }

    fun lore(vararg lines: Component): Item {
        return lore(lines.toCollection(ArrayList()))
    }

    fun amount(number: Int): Item {
        stack.amount = number
        return this
    }

}