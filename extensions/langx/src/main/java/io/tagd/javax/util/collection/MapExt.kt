package io.javax.util.collection

import java.util.AbstractMap

fun <K, V> AbstractMap<K, V>.getKeys(): List<K> {
    val keys = arrayListOf<K>()
    this.entries.forEach {
        keys.add(it.key)
    }
    return keys
}