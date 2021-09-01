package io.tagd.arch.present.mvp

import io.tagd.core.Releasable

interface Presenter<V : PresentableView> : Releasable {

    val view: V?

    fun onCreate()

    fun onStart()

    fun onResume()

    fun onAwaiting()

    fun onReady()

    fun onPause()

    fun onStop()

    fun onDestroy()

    fun onBackPressed()

    fun canHandleBackPress(): Boolean
}
