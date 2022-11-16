package me.mrenxo.utilase.GUI

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority.LOWEST
import org.bukkit.event.EventPriority.MONITOR
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.DoubleChestInventory
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

data class Coords(
    var x: Int,
    var y: Int
) {}

fun toSlot(x: Int, y: Int) = x + (y * 9)
fun fromSlot(s: Int) = Pair(s % 9, s / 9)

fun gui(
    title: Component,
    rows: Int,
    render: GUI.() -> Unit
) = GUI(title, rows, render).apply(render)

fun Player.open(gui: GUI) = openInventory(gui.inventory)

private lateinit var plugin: JavaPlugin;

public fun setPlugin(_plugin: JavaPlugin) {
    plugin = _plugin
}


class GUI(
    val title: Component,
    val rows: Int,
    val render: GUI.() -> Unit
) : Listener {
    init {
        Bukkit.getServer().pluginManager.registerEvents(this, plugin)
    }

    private val slots = arrayOfNulls<Slot>(9 * rows)
    private val formatMap = HashMap<Int, Boolean>()
    private var onclose: InventoryCloseEvent.(Player) -> Unit = {}

    val inventory = Bukkit.getServer().createInventory(null, rows * 9, title)

    fun refresh() {
        inventory.clear()
        for (i in 0 until 9 * rows)
            slots[i] = null
        this.render()
    }

    @EventHandler(priority = LOWEST)
    fun onclick(e: InventoryClickEvent) {
        if (e.inventory != inventory) return
        val slot = slots.getOrNull(e.slot)
        if (slot == null || slot.formatted) e.isCancelled = true

        if (e.clickedInventory != inventory) return
        val player = e.whoClicked as? Player ?: return
        if (slot == null) return;

        slot.onclick(e, player)
    }

    @EventHandler(priority = MONITOR)
    fun onclose(e: InventoryCloseEvent) {
        if (e.inventory != inventory) return
        if (inventory.viewers.size > 0) return

        onclose(e)
        HandlerList.unregisterAll(this)
    }



    inner class Slot() {
        var item: Item? = null
        var formatted: Boolean = true
        var onclick: InventoryClickEvent.(Player) -> Unit = {}
    }

    private fun slot(
        i: Int,
        builder: GUI.Slot.() -> Unit
    ) {
        val slot = Slot().apply(builder)
        inventory.setItem(i, slot.item?.stack)
        slots[i] = slot
    }

    private fun slot(
        x: Int, y: Int,
        builder: GUI.Slot.() -> Unit
    ) = slot(toSlot(x, y), builder)


    fun slot(
        coords: Coords,
        builder: GUI.Slot.() -> Unit
    ) = slot(coords.x, coords.y, builder)

    private fun fill(
        x1: Int, y1: Int, x2: Int, y2: Int,
        builder: GUI.Slot.() -> Unit
    ) {
        val dx = if (x1 < x2) x1..x2 else x2..x1
        val dy = if (y1 < y2) y1..y2 else y2..y1
        for (x in dx) for (y in dy) slot(x, y, builder)
    }

    fun fill(
        coords1: Coords,
        coords2: Coords,
        builder: GUI.Slot.() -> Unit
    ) = fill(coords1.x, coords1.y, coords2.x, coords2.y, builder)

    fun all(builder: GUI.Slot.() -> Unit) = fill(0, 0, 8, rows - 1, builder)

    fun Coords.up() {
        y = Math.floorMod((y - 1), rows)
    }

    fun Coords.down() {
        y = Math.floorMod((y + 1), rows)
    }

    fun Coords.left() {
        x = Math.floorMod((x - 1), 9)
    }

    fun Coords.right() {
        x = Math.floorMod((x + 1), 9)
    }
}
