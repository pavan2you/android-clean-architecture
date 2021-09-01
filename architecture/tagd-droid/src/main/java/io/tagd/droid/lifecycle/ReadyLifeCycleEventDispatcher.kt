package io.tagd.droid.lifecycle

import io.tagd.droid.launch.AppService
import java.lang.ref.WeakReference
import java.util.*

class ReadyLifeCycleEventDispatcher : AppService {

    private var queue : Queue<WeakReference<ReadyLifeCycleEventOwner>>? = ArrayDeque()
    private var ready: Boolean = false

    fun register(owner: ReadyLifeCycleEventOwner) {
        queue?.offer(WeakReference(owner))
    }

    fun unregister(owner: ReadyLifeCycleEventOwner) {
        queue?.firstOrNull {
            it?.get() === owner
        }?.let {
            queue?.remove(it)
        }
    }

    fun dispatchOnReady() {
        ready = true
        queue?.forEach {
            it?.get()?.onReady()
        }
        clearRegistry()
    }

    fun ready() : Boolean {
        return ready
    }

    private fun clearRegistry() {
        queue?.forEach {
            it?.clear()
        }
        queue?.clear()
    }

    override fun release() {
        clearRegistry()
        queue = null
    }
}