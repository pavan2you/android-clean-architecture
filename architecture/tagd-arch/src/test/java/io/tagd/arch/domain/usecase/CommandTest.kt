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

package io.tagd.arch.domain.usecase

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.spy
import io.tagd.arch.present.service.PresentationService
import io.tagd.di.Global
import io.tagd.di.layer
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CommandTest {

    private val command: Command<Unit, String> = spy()
    private val callback: Callback<Any?> = spy()

    @After
    fun tearDown() {
        reset(command, callback)
    }

    @Test
    fun `given execute is called verify it is handled`() {
        var called = false
        `when`(command.execute()).thenAnswer {
            called = true
            return@thenAnswer Unit
        }

        command.execute()

        assert(called)
    }

    @Test
    fun `given last result is called verify it is handled`() {
        `when`(command.lastResult()).thenReturn("hello")
        val result = command.lastResult()
        assert(result == "hello")
    }

    @Test
    fun `given callback is called then verify it is handled`() {
        var called = false
        `when`(callback.invoke(null)).thenAnswer {
            called = true
            return@thenAnswer Unit
        }

        callback.invoke(null)

        assert(called)
    }

    @Test
    fun `verify usecase access for bounded service`() {
        with(Global) {
            layer<Command<*, *>> {
                bind<Command<*, *>>().toInstance(mock())
            }
        }

        val service = Command.usecase<Command<*, *>>()
        assert(service != null)
    }

    @Test
    fun `verify createUsecase access for bounded service`() {
        val service1 = FakeUsecase()
        with(Global) {
            layer<Command<*, *>> {
                bind<FakeUsecase>().toCreator { service1 }
            }
        }

        val service2 = Command.createUsecase<FakeUsecase>()
        assert(service1 === service2)
    }

    class FakeUsecase : Command<Any, Any> {
        override fun release() {
        }

        override fun cancel(context: Any?): Boolean {
            return true
        }

        override fun execute(
            args: Args?,
            success: Callback<Any>?,
            failure: Callback<Throwable>?
        ): Any {
            return "fake"
        }

        override fun lastResult(args: Args?): Any? {
            return "fake"
        }
    }
}