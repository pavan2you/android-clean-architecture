package io.tagd.droid.mvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.tagd.arch.control.IApplication
import io.tagd.arch.present.mvp.PresentableView
import io.tagd.arch.present.mvp.Presenter
import io.tagd.droid.launch.TagdApplication
import io.tagd.droid.lifecycle.ReadyLifeCycleEventDispatcher
import io.tagd.droid.lifecycle.ReadyLifeCycleEventOwner

abstract class MvpFragment<V : PresentableView, P : Presenter<V>> : Fragment(), PresentableView,
    ReadyLifeCycleEventOwner {

    protected var presenter: P? = null

    override val app: IApplication?
        get() = context?.applicationContext as? IApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = onCreatePresenter(savedInstanceState)
        presenter?.onCreate()
        onCreateView(savedInstanceState)
    }

    protected abstract fun onCreatePresenter(savedInstanceState: Bundle?): P?

    protected abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun <V : PresentableView> presenter(): Presenter<V>? = presenter as? Presenter<V>

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
        if (readyLifeCycleEventDispatcher().ready()) {
            onReady()
        } else {
            readyLifeCycleEventDispatcher().register(this)
            onAwaiting()
        }
    }

    override fun readyLifeCycleEventDispatcher(): ReadyLifeCycleEventDispatcher {
        return (context?.applicationContext as TagdApplication).appService()!!
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun onPause() {
        presenter?.onPause()
        super.onPause()
    }

    override fun onStop() {
        presenter?.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        release()
        super.onDestroy()
    }

    fun onBackPressed() {
        if (presenter != null && presenter?.canHandleBackPress() == true) {
            presenter?.onBackPressed()
        }
    }

    override fun release() {
        readyLifeCycleEventDispatcher().unregister(this)
        presenter = null
    }
}