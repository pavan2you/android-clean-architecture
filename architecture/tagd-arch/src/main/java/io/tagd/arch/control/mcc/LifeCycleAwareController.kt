package io.tagd.arch.control.mcc

import java.lang.ref.WeakReference

open class LifeCycleAwareController<C : Controllable>(controllable: C) : Controller<C> {

    private var controllableReference: WeakReference<C>? = WeakReference(controllable)

    override val controllable: C?
        get() = controllableReference?.get()

    override fun onCreate() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        release()
    }

    override fun release() {
        controllableReference?.clear()
        controllableReference = null
    }
}