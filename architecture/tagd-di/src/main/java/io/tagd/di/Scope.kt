package io.tagd.di

import io.tagd.core.Releasable
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.Scope.Companion.GLOBAL_SCOPE

open class Scope(val name: String = GLOBAL_SCOPE) : Releasable {

    private var mutableLocator: Locator? = LayerLocator()
    private var scopes: MutableMap<String, Scope>? = mutableMapOf()
    private var mutableState: State? = State()

    val locator: Locator
        get() = mutableLocator!!

    val state: State
        get() = mutableState!!

    fun with(locator: Locator): Scope {
        release()
        mutableLocator = locator
        return this
    }

    fun with(state: State): Scope {
        mutableState?.release()
        mutableState = state
        return this
    }

    fun addSubScope(scope: Scope): Scope {
        if (scope.name == GLOBAL_SCOPE) {
            throw IllegalAccessException("global scope can not be a sub scope")
        }
        scopes?.put(scope.name, scope)
        return this
    }

    fun subScopes(): Collection<Scope>? {
        return scopes?.values
    }

    fun subScope(name: String): Scope? {
        var value = scopes?.get(name)
        if (value == null) {
            val scopes = subScopes()
            if (scopes != null) {
                for (scope in scopes) {
                    value = scope.subScope(name)
                    if (value != null) {
                        break
                    }
                }
            }
        }
        return value
    }

    fun removeSubScope(name: String): Scope? {
        var value = scopes?.remove(name)
        if (value == null) {
            val scopes = subScopes()
            if (scopes != null) {
                for (scope in scopes) {
                    value = scope.removeSubScope(name)
                    if (value != null) {
                        break
                    }
                }
            }
        }
        value?.release()
        return value
    }

    fun reset() {
        releaseSubScopes()
        mutableLocator = LayerLocator()
        mutableState = State()
    }

    override fun release() {
        releaseSubScopes()
        scopes = null

        mutableLocator?.release()
        mutableLocator = null

        mutableState?.release()
        mutableState = null
    }

    private fun releaseSubScopes() {
        subScopes()?.forEach {
            it.release()
        }
        scopes?.clear()
    }

    companion object {
        const val GLOBAL_SCOPE = "global"
    }
}

fun scope(name: String, parent: Scope? = Global, bindings: Scope.() -> Unit): Scope {
    val scope = if (name == GLOBAL_SCOPE) {
        if (parent == null || parent  == Global) {
            Global
        } else {
            throw IllegalAccessException("global scope can not be a sub scope")
        }
    } else {
        val scope = Scope(name)
        (parent ?: Global).addSubScope(scope)
        scope
    }
    return scope.apply {
        bindings()
    }
}

inline fun <reified T : Service> Scope.layer(bindings: Layer<T>.() -> Unit): Layer<T> {
    return locator.layer(bindings)
}

fun <T : Service, S : T> Scope.get(clazz: Key<S>): S? {
    var value: S? = locator.get(clazz)
    if (value == null) {
        val scopes = subScopes()
        if (scopes != null) {
            for (scope in scopes) {
                value = scope.get(clazz)
                if (value != null) {
                    break
                }
            }
        }
    }
    return value
}

fun <T : Service, S : T> Scope.create(key: Key<S>, args: State? = null): S {
    var value: S? = null
    var exception: Exception? = null

    try {
        value = locator.create(key, args)
    } catch (e: Exception) {
        exception = e

        val scopes = subScopes()
        if (scopes != null) {
            for (scope in scopes) {
                try {
                    value = scope.create(key, args)
                    break
                } catch (e: Exception) {
                    //ignore
                }
            }
        }
    }

    return value ?: throw exception!!
}

object Global : Scope()