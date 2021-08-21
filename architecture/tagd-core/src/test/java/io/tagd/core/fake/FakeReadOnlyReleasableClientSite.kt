package io.tagd.core.fake

import io.tagd.core.Releasable

class FakeReadOnlyReleasableClientSite(releasable: Releasable) {

    var mutableReleasable: Releasable? = releasable

    val releasable: Releasable
        get() = mutableReleasable!!

    fun dispatchRelease() {
        mutableReleasable = null
    }
}