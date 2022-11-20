package me.mrenxo.utilase.formating

import java.text.DecimalFormat




fun wrap(text: String, max: Int = 32) = text.split("\n").flatMap {
    val list = mutableListOf<String>()
    val words = it.split(" ")
    var line = words[0]

    for (word in words.drop(1)) {
        val pre = "$line $word"

        if (pre.length > max) {
            list += line
            line = word
        } else line = pre
    }

    list += line
    list
}

fun formatNum(number: Number): String? {
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'Q', 'S')
    val numValue = number.toLong()
    val value = Math.floor(Math.log10(numValue.toDouble())).toInt()
    val base = value / 3
    return if (value >= 3 && base < suffix.size) {
        DecimalFormat("#0.0").format(numValue / Math.pow(10.0, (base * 3).toDouble())) + suffix[base]
    } else {
        DecimalFormat("#,##0").format(numValue)
    }
}