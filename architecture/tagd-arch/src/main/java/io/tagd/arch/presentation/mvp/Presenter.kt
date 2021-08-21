package io.tagd.arch.presentation.mvp

import io.tagd.core.Releasable

interface Presenter<V : PresentableView> : Releasable {

    val view: V?

    fun onCreate()

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

    fun onBackPressed()

    fun canHandleBackPress(): Boolean
}
