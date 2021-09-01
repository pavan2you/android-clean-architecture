package io.tagd.droid.launch

import io.tagd.arch.infra.InfraService
import io.tagd.di.Global
import io.tagd.di.layer
import io.tagd.droid.lifecycle.ReadyLifeCycleEventDispatcher
import java.lang.ref.WeakReference

open class Injector(application: TagdApplication) : AppService {

    protected var appReference: WeakReference<TagdApplication>? = WeakReference(application)

    protected val app: TagdApplication?
        get() = appReference?.get()

    open fun inject() {
        val application = app!!
        with(Global) {
            injectAppServicesLayer(application)
        }
    }

    protected open fun Global.injectAppServicesLayer(application: TagdApplication) {
        layer<InfraService> {
            bind<AppForegroundBackgroundSenser>().toInstance(
                AppForegroundBackgroundSenser(application)
            )
            bind<ReadyLifeCycleEventDispatcher>().toInstance(
                ReadyLifeCycleEventDispatcher()
            )
        }
    }

    override fun release() {
        appReference?.clear()
        appReference = null
    }

    companion object {
        fun setInjector(injector: Injector) {
            with(Global) {
                layer<InfraService> {
                    bind<Injector>().toInstance(injector)
                }
            }
        }
    }
}


