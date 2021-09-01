package io.tagd.app

import android.os.Bundle
import io.tagd.app.R.layout
import io.tagd.arch.present.mvp.LifeCycleAwarePresenter
import io.tagd.arch.present.mvp.PresentableView
import io.tagd.droid.mvp.MvpActivity

interface UsageView : PresentableView {

    fun showCallerView()
}

class UsagePresenter(view : UsageView) : LifeCycleAwarePresenter<UsageView>(view) {

    override fun onBackPressed() {
        view?.showCallerView()
    }
}

class UsageActivity : MvpActivity<UsageView, UsagePresenter>(), UsageView {

    override fun onCreatePresenter(savedInstanceState: Bundle?): UsagePresenter {
        return UsagePresenter(this)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
    }

    override fun onAwaiting() {
        super.onAwaiting()
        setContentView(layout.usage_waiting_view)
    }

    override fun onReady() {
        super.onReady()
        setContentView(layout.usage_view)

        val injector = AppInjector()
        injector.setup(this)
        val usage = Usage()
        usage.use()
        usage.release()
    }

    override fun showCallerView() {
        finish()
    }

    override fun release() {
    }
}