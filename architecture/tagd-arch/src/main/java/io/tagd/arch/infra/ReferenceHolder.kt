package io.tagd.arch.infra

import io.tagd.core.Service

open class ReferenceHolder<T>(value: T) : Service {

    private var mutableValue: T? = value

    val value: T?
        get() = mutableValue

    override fun release() {
        mutableValue = null
    }
}