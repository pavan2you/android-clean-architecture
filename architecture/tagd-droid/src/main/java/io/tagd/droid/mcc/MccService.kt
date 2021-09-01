package io.tagd.droid.mcc

import android.app.Service
import android.content.Intent
import io.tagd.arch.control.IApplication
import io.tagd.arch.control.mcc.Controllable
import io.tagd.arch.control.mcc.Controller
import io.tagd.droid.launch.TagdApplication
import io.tagd.droid.lifecycle.ReadyLifeCycleEventDispatcher
import io.tagd.droid.lifecycle.ReadyLifeCycleEventOwner

abstract class MccService<C : Controllable, L : Controller<C>> : Service(), Controllable,
    ReadyLifeCycleEventOwner {

    protected var controller: L? = null

    override val app: IApplication?
        get() = application as? IApplication

    override fun onCreate() {
        super.onCreate()
        controller = onCreateController()
        controller?.onCreate()
    }

    protected abstract fun onCreateController(): L?

    override fun readyLifeCycleEventDispatcher(): ReadyLifeCycleEventDispatcher? {
        return (application as TagdApplication).appService()!!
    }

    override fun <C : Controllable> controller(): Controller<C>? = controller as? Controller<C>

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        controller?.onStart()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onAwaiting() {
        controller?.onAwaiting()
    }

    override fun onReady() {
        controller?.onReady()
    }

    fun dispatchStop() {
        controller?.onStop()
        stopSelf()
    }

    fun dispatchStop(startId: Int) {
        controller?.onStop()
        stopSelf(startId)
    }

    fun dispatchStopResult(startId: Int) : Boolean {
        controller?.onStop()
        return stopSelfResult(startId)
    }

    override fun onDestroy() {
        controller?.onDestroy()
        release()
        super.onDestroy()
    }

    override fun release() {
        controller = null
    }
}