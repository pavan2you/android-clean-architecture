package io.tagd.droid.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.tagd.arch.present.mvp.PresentableView
import io.tagd.arch.present.mvp.Presenter

abstract class MvpActivity<V : PresentableView, P : Presenter<V>> : AppCompatActivity(),
    PresentableView {

    protected var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = onCreatePresenter(savedInstanceState)
        presenter?.onCreate()
        onCreateView(savedInstanceState)
    }

    protected abstract fun onCreatePresenter(savedInstanceState: Bundle?): P?

    protected abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
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

    override fun onBackPressed() {
        if (presenter != null && presenter?.canHandleBackPress() == true) {
            presenter?.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    override fun release() {
        presenter = null
    }
}