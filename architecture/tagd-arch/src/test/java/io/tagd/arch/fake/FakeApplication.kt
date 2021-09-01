package io.tagd.arch.fake

import io.tagd.arch.control.ApplicationController
import io.tagd.arch.control.IApplication
import io.tagd.arch.control.LifeCycleAwareApplicationController

class FakeApplication : IApplication {

    var controller: ApplicationController<IApplication>? =
        LifeCycleAwareApplicationController(this)

    override fun <A : IApplication> controller(): ApplicationController<A>? {
        return controller as? ApplicationController<A>
    }

    override fun release() {
        controller?.onDestroy()
        controller = null
    }
}