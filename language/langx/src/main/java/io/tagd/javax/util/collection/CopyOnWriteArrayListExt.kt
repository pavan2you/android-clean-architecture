package io.tagd.javax.util.collection

import java.util.concurrent.CopyOnWriteArrayList

fun <T> CopyOnWriteArrayList<T>.removeAllByFilter(predicate: (T) -> Boolean) {
    val toRemove = this.filter {
        predicate.invoke(it)
    }
    removeAll(toRemove)
}