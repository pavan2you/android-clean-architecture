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

package io.tagd.core

import io.tagd.core.fake.FakeCancellable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CancellableTest {

    private val cancellable: Cancellable = FakeCancellable()

    @Test
    fun `given cancel is called and if cancellation is successful then verify result is true`() {
        cancellable as FakeCancellable
        assert(!cancellable.cancelled)
        cancellable.cancel()
        assert(cancellable.cancelled)
    }

    @Test
    fun `given cancel with context and if cancellation is successful then verify result is true`() {
        cancellable as FakeCancellable
        assert(!cancellable.cancelled)
        cancellable.cancel(context = "context")
        assert(cancellable.cancelled)
    }

    @Test
    fun `given cancel with invalid context then verify result is false`() {
        cancellable as FakeCancellable
        assert(!cancellable.cancelled)
        cancellable.cancel(context = "invalid")
        assert(!cancellable.cancelled)
    }
}