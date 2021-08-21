package io.tagd.di

import io.tagd.core.Releasable
import io.tagd.core.Service
import io.tagd.core.State

class Layer<T : Service> : Releasable {

    private var services: MutableMap<Key<out T>, Value<T>>? = mutableMapOf()

    inline fun <reified S : T> bind(key: Key<S>? = null): Binding<T, S> {
        return Binding(this, key ?: Key(S::class.java))
    }

    fun <S : T> bind(service: Key<S>, instance: S) {
        services?.put(service, GetValue(instance))
    }

    fun <S : T> bind(service: Key<S>, creator: (State?) -> S) {
        services?.put(service, CreateValue(creator))
    }

    fun contains(service: Key<*>): Boolean? {
        var contains = services?.containsKey(service)
        if (contains == null || contains == false) {
            if (service.key is Class<*>) {
                contains = services?.filterKeys {
                    it.key == service.key
                }?.isNotEmpty()
            }
        }
        return contains
    }

    @Suppress("UNCHECKED_CAST")
    fun <S : T> get(service: Key<*>): S? {
        return services?.get(service)?.get() as S?
    }

    @Suppress("UNCHECKED_CAST")
    fun <S : T> create(service: Key<S>, args: State? = null): S {
        return services?.get(service)?.get(args) as? S
            ?: throw IllegalAccessException("No creator available for $service")
    }

    override fun release() {
        services?.clear()
        services = null
    }

    class Binding<T : Service, S : T>(private val layer: Layer<T>, private val key: Key<S>) {

        fun toInstance(instance: S) {
            layer.bind(key, instance)
        }

        fun toCreator(creator: (State?) -> S) {
            layer.bind(key, creator)
        }
    }
}