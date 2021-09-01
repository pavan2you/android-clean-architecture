package io.tagd.arch.control

import io.tagd.core.Releasable

interface ApplicationController<A : IApplication> : Releasable {

    val app: A?

    fun onCreate()

    fun onLaunch()

    fun onLoading()

    fun onReady()

    fun onForeground()

    fun onBackground()

    fun onDestroy()
}