package io.tagd.arch.control.mcc

import java.lang.ref.WeakReference

open class LifeCycleAwareController<C : Controllable>(controllable: C) : Controller<C> {

    private var controllableReference: WeakReference<C>? = WeakReference(controllable)

    override val controllable: C?
        get() = controllableReference?.get()

    override fun onCreate() {
        //no op
    }

    override fun onStart() {
        //no op
    }

    override fun onAwaiting() {
        //no op
    }

    override fun onReady() {
        //no op
    }

    override fun onStop() {
        //no op
    }

    override fun onDestroy() {
        release()
    }

    override fun release() {
        controllableReference?.clear()
        controllableReference = null
    }
}