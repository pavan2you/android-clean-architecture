package io.tagd.core.fake

import io.tagd.core.Releasable

class FakeReleasable : Releasable {

    private var mutableValue: Any? = null

    val value: Any
        get() = mutableValue!!

    val nullableValue: Any?
        get() = mutableValue

    init {
        mutableValue = "SomeValue"
    }

    override fun release() {
        mutableValue = null
    }
}