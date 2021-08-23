package io.tagd.arch.domain.crosscutting.codec

import io.tagd.arch.domain.crosscutting.CrossCutting
import java.lang.reflect.Type

interface JsonCodec : CrossCutting {

    fun toJson(obj: Any): String

    fun <T> fromJson(json: String, clazz: Class<T>): T

    fun <T> fromJson(json: String, type: Type): T
}
