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

import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.spy
import junit.framework.TestCase
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StateTest {

    private val state: State = spy()

    @After
    fun tearDown() {
        reset(state)
    }

    @Test
    fun `given no-data then verify state is empty`() {
        assert(state.isEmpty())
    }

    @Test
    fun `given data then verify state is not empty`() {
        state.put("float", 1.0f)
        assert(!state.isEmpty())
    }

    @Test
    fun `given invalid key then verify result is null`() {
        assert(state.get<Any>("somekey") == null)
    }

    @Test
    fun `given an invalid key with specific type then verify result is null`() {
        assert(state.get<String>("somekey") == null)
    }

    @Test(expected = ClassCastException::class)
    fun `given data when cast to other type then verify ClassCastException is thrown`() {
        state.put("float", 1.0f)
        assert(state.get<Double>("float") == 1.0)
    }

    @Test
    fun `given data is present then verify they are castable to respective type`() {
        state.put("boolean", true)
        state.put("int", 1)
        state.put("float", 1.0f)
        state.put("char", 'c')
        state.put("byte", 99)
        state.put("short", 1000)
        state.put("long", 123456789L)
        state.put("double", 1.0)
        state.put("string", "name")
        state.put("pair", Pair("hello", "world"))

        assert(state.get<Boolean>("boolean") == true)
        assert(state.get<Int>("int") == 1)
        assert(state.get<Float>("float") == 1.0f)
        assert(state.get<Char>("char") == 'c')
        TestCase.assertEquals(state.get<Byte>("byte"), 99)
        TestCase.assertEquals(state.get<Short>("short"), 1000)
        assert(state.get<Long>("long") == 123456789L)
        assert(state.get<Double>("double") == 1.0)
        assert(state.get<String>("string") == "name")
        assert(state.get<Pair<String, String>>("pair") == Pair("hello", "world"))
    }

    @Test
    fun `given putAll is called then verify State contains every element of putAll`() {
        state.putAll(
            arrayOf("one" to 1, "two" to 2)
        )

        assert(state.get<Int>("one") == 1)
        assert(state.get<Int>("two") == 2)
    }

    @Test
    fun `given data is cleared then verify State is empty`() {
        state.put("float", 1.0f)
        assert(!state.isEmpty())

        state.clear()
        assert(state.isEmpty())
    }

    @Test
    fun `given State is released then verify State is empty`() {
        state.put("float", 1.0f)
        assert(!state.isEmpty())

        state.release()
        assert(state.isEmpty())
    }

    @Test
    fun `given stateOf is called then verify State object is created`() {
        val state = stateOf("one" to 1)
        assert(state.get<Int>("one") == 1)
    }

    @Test
    fun `given two State objects with data then verify they are equal`() {
        val state1 = stateOf("one" to 1)
        val state2 = stateOf("one" to 1)
        assert(state1 == state2)
        assert(state1.hashCode() == state2.hashCode())
        assert(state1.toString() == state2.toString())
    }
}