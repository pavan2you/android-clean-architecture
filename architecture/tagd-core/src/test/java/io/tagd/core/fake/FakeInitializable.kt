package io.tagd.core.fake

import io.tagd.core.Initializable

class FakeInitializable : Initializable {

    var initialized : Boolean = false

    init {
        initialize()
    }

    override fun initialize() {
        initialized = true
    }
}