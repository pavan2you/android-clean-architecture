package io.tagd.core.fake

import io.tagd.core.Releasable

class FakeReleasableOwnerClientSite() {

    private var mutableReleasable: Releasable? = FakeReleasable()

    val releasable: Releasable
        get() = mutableReleasable!!

    val nullableReleasable: Releasable?
        get() = mutableReleasable

    fun dispatchRelease() {
        mutableReleasable?.release()
        mutableReleasable = null
    }
}