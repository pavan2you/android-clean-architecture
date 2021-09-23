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

import io.tagd.arch.domain.crosscutting.async.cancelAsync
import io.tagd.arch.domain.crosscutting.async.compute
import io.tagd.droid.launch.Injector
import io.tagd.droid.launch.TagdApplication

class SampleApplication : TagdApplication() {

    override fun setupInjector() {
        Injector.setInjector(AppInjector(this))
    }

    override fun onLoading() {
        // instead of handler | coroutine job context/scope
//        Handler(Looper.getMainLooper()).postDelayed({
//            dispatchOnLoadingComplete()
//        }, 5000)

        // an abstracted way of scheduling work - it doesn't matter which scheduling approach in
        // beneath
        compute(this, 5000) {
            dispatchOnLoadingComplete()
        }
    }

    override fun release() {
        // gracefully cancel pending tasks
        cancelAsync(this)
        super.release()
    }
}