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

package io.tagd.arch.fake

import io.tagd.arch.control.ApplicationController
import io.tagd.arch.control.IApplication
import io.tagd.arch.control.LifeCycleAwareApplicationController

class FakeApplication : IApplication {

    var controller: ApplicationController<IApplication>? =
        LifeCycleAwareApplicationController(this)

    override fun <A : IApplication> controller(): ApplicationController<A>? {
        return controller as? ApplicationController<A>
    }

    override fun release() {
        controller?.onDestroy()
        controller = null
    }
}