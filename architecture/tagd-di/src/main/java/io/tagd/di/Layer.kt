/*
 * Copyright (C) 2021 The TagD Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.tagd.di

import io.tagd.core.Releasable
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.core.Layerable

class Layer<T : Service> : Layerable, Releasable {

    private var services: MutableMap<Keyable<out T>, Value<T>>? = mutableMapOf()

    inline fun <reified S : T> bind(key: Keyable<S>? = null): Binding<T, S> {
        return Binding(this, key ?: Key(S::class.java))
    }

    fun <S : T> bind(service: Keyable<S>, instance: S) {
        services?.put(service, GetValue(instance))
    }

    fun <S : T> bind(service: Keyable<S>, creator: (State?) -> S) {
        services?.put(service, CreateValue(creator))
    }

    fun contains(service: Keyable<*>): Boolean? {
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
    fun <S : T> get(service: Keyable<*>): S? {
        return services?.get(service)?.get() as S?
    }

    @Suppress("UNCHECKED_CAST")
    fun <S : T> create(service: Keyable<S>, args: State? = null): S {
        return services?.get(service)?.get(args) as? S
            ?: throw IllegalAccessException("No creator available for $service")
    }

    override fun release() {
        services?.clear()
        services = null
    }

    class Binding<T : Service, S : T>(private val layer: Layer<T>, private val key: Keyable<S>) {

        fun toInstance(instance: S) {
            layer.bind(key, instance)
        }

        fun toCreator(creator: (State?) -> S) {
            layer.bind(key, creator)
        }
    }
}