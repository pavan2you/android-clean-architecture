package io.tagd.arch.control

import java.lang.ref.WeakReference

class LifeCycleAwareApplicationController(application: IApplication) :
    ApplicationController<IApplication> {

    private var applicationReference: WeakReference<IApplication>? = WeakReference(application)

    override val app: IApplication?
        get() = applicationReference?.get()

    override fun onCreate() {
        //no op
    }

    override fun onLaunch() {
        //no op
    }

    override fun onLoading() {
        //no op
    }

    override fun onReady() {
        //no op
    }

    override fun onForeground() {
        //no op
    }

    override fun onBackground() {
        //no op
    }

    override fun onDestroy() {
        release()
    }

    override fun release() {
        //no op
    }
}