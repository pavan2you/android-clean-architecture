/*
 * Copyright (C) 2021 The TagD Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.tagd.app

import android.os.Bundle
import android.widget.Toast
import io.tagd.arch.domain.crosscutting.async.cancelAsync
import io.tagd.arch.domain.crosscutting.async.compute
import io.tagd.arch.domain.crosscutting.async.present
import io.tagd.arch.present.mvp.LifeCycleAwarePresenter
import io.tagd.arch.present.mvp.PresentableView
import io.tagd.droid.mvp.MvpActivity

interface UsageView : PresentableView {

    fun showMessage(message: String)

    fun showCallerView()
}

class UsagePresenter(view : UsageView) : LifeCycleAwarePresenter<UsageView>(view) {

    override fun onReady() {
        super.onReady()

        val timeTakingWorkDuration = 5000L
        compute(this, timeTakingWorkDuration) {
            view?.showMessage("I've finished a long running computation work")
        }
    }

    override fun onBackPressed() {
        view?.showCallerView()
    }

    override fun onDestroy() {
        cancelAsync(this)
        super.onDestroy()
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
        setContentView(R.layout.usage_waiting_view)
    }

    override fun onReady() {
        super.onReady()
        setContentView(R.layout.usage_view)

        val usage = Usage()
        usage.use()
        usage.release()
    }

    override fun showMessage(message: String) {
        present(this) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun showCallerView() {
        finish()
    }

    override fun release() {
        cancelAsync(this)
    }
}