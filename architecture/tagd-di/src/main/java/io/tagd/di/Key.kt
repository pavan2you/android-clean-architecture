package io.tagd.di

import io.tagd.core.Service

class Key<T : Service>(val key: Any) {

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

inline fun <reified T : Service> key(): Key<T> = Key(typeOf<T>())

inline fun <reified T : Service> key(key: String): Key<T> = Key(key)

inline fun <reified T : Service> key(vararg typeClasses: Class<*>): Key<T> =
    Key(TypedClass(T::class.java, typeClasses))

inline fun <reified T : Service, reified S : Any> key2(): Key<T> =
    Key(typedClassOf<T, S>())

inline fun <reified T : Service, reified S : Any> typedClassOf(): TypedClass<T> =
    TypedClass(T::class.java, arrayOf(typeOf<S>()))

inline fun <reified T : Service, reified R : Any, reified S : Any> key3(): Key<T> =
    Key(TypedClass(T::class.java, arrayOf(typeOf<R>(), typeOf<S>())))
