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

import io.tagd.core.Service

interface Keyable<T> {

    val key: Any
}

class Key<T : Service>(override val key: Any) : Keyable<T> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Key<*>

        if (key != other.key) return false

        return true
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}

class TypedClass<T : Service>(
    val clazz: Class<T>,
    val typeClasses: Array<out Class<*>>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TypedClass<*>

        if (clazz != other.clazz) return false
        if (!typeClasses.contentEquals(other.typeClasses)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = clazz.hashCode()
        result = 31 * result + typeClasses.contentHashCode()
        return result
    }
}

inline fun <reified T : Any> typeOf(): Class<T> = T::class.java

inline fun <reified T : Service> key(): Keyable<T> = Key(typeOf<T>())

inline fun <reified T : Service> key(key: String): Key<T> = Key(key)

inline fun <reified T : Service> key(vararg typeClasses: Class<*>): Keyable<T> =
    Key(TypedClass(T::class.java, typeClasses))

inline fun <reified T : Service, reified S : Any> key2(): Keyable<T> =
    Key(typedClassOf<T, S>())

inline fun <reified T : Service, reified S : Any> typedClassOf(): TypedClass<T> =
    TypedClass(T::class.java, arrayOf(typeOf<S>()))

inline fun <reified T : Service, reified R : Any, reified S : Any> key3(): Keyable<T> =
    Key(TypedClass(T::class.java, arrayOf(typeOf<R>(), typeOf<S>())))
