package io.tagd.core

/**
 * A unified state passing mechanism across the architecture objects, where the argument
 * sequence would be 1 to N. Instead of various overloaded methods, this would be considered as
 * an alternative.
 */
open class State : Releasable {

    private val map = mutableMapOf<String, Any?>()

    /**
     * Put an argument's key, value in State.
     */
    fun put(key: String, value: Any?) {
        map[key] = value
    }

    /**
     * Access an argument by its key.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T? {
        return map[key] as T?
    }

    /**
     * Put all arguments key, value pairs in State.
     */
    fun putAll(pairs: Array<out Pair<String, Any?>>) {
        map.putAll(pairs)
    }

    /**
     * find out whether the state is empty
     */
    fun isEmpty() = map.isEmpty()

    /**
     * find out clear
     */
    fun clear() {
        map.clear()
    }

    override fun release() {
        clear()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as State

        if (map != other.map) return false

        return true
    }

    override fun hashCode(): Int {
        return map.hashCode()
    }

    override fun toString(): String {
        return "State(map=$map)"
    }
}

/**
 * A factory method to create a State with give key-value [Pair]s.
 */
fun stateOf(vararg pairs: Pair<String, Any?>): State {
    return State().apply {
        putAll(pairs)
    }
}