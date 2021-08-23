package io.tagd.arch.fake

import io.tagd.arch.domain.crosscutting.async.*

open class FakeAsyncStrategy : AsyncStrategy, PresentationStrategy, ComputationStrategy,
    NetworkIOStrategy, DiskIOStrategy, DaoStrategy {

    var released: Boolean = false

    override fun execute(context: Any?, work: () -> Unit) {
        work.invoke()
    }

    override fun release() {
        released = true
    }

    override fun cancel(context: Any?): Boolean {
        return true
    }
}