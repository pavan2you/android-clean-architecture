package io.tagd.arch.present.mvp

import io.tagd.core.annotation.VisibleForTesting
import java.lang.ref.WeakReference

open class LifeCycleAwarePresenter<V : PresentableView>(view: V) : Presenter<V> {

    private var viewReference: WeakReference<V>? = WeakReference(view)

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    var canHandleBackPress: Boolean? = true

    override val view: V?
        get() = viewReference?.get()

    override fun onCreate() {
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        release()
    }

    override fun onBackPressed() {
    }

    override fun canHandleBackPress(): Boolean {
        return canHandleBackPress ?: false
    }

    override fun release() {
        viewReference?.clear()
        viewReference = null
        canHandleBackPress = null
    }
}