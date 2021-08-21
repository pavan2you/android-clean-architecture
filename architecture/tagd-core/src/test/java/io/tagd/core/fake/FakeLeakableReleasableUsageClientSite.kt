package io.tagd.core.fake

import io.tagd.core.Releasable
import java.lang.ref.WeakReference

class FakeLeakableReleasableUsageClientSite(leakableReleasable: Releasable) {

    private var releasableReference: WeakReference<Releasable>? = WeakReference(leakableReleasable)

    val releasable: Releasable
        get() = releasableReference?.get()!!

    val nullableReleasable: Releasable?
        get() = releasableReference?.get()

    fun dispatchRelease() {
        releasableReference?.clear()
        releasableReference = null
    }
}