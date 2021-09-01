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
        //no op
    }

    override fun onStart() {
        //no op
    }

    override fun onResume() {
        //no op
    }

    override fun onAwaiting() {
        //no op
    }

    override fun onReady() {
        //no op
    }

    override fun onPause() {
        //no op
    }

    override fun onStop() {
        //no op
    }

    override fun onDestroy() {
        release()
    }

    override fun onBackPressed() {
        //no op
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