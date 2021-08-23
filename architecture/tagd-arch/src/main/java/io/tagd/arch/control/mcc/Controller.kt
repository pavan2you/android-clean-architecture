package io.tagd.arch.control.mcc

import io.tagd.core.Releasable

interface Controller<C : Controllable> : Releasable {

    val controllable: C?

    fun onCreate()

    fun onStart()

    fun onStop()

    fun onDestroy()
}