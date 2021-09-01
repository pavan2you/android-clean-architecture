package io.tagd.droid.lifecycle

interface ReadyLifeCycleEventOwner {

    fun readyLifeCycleEventDispatcher() : ReadyLifeCycleEventDispatcher?

    fun onAwaiting()

    fun onReady()
}