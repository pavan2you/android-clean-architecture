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

import io.tagd.core.Service
import io.tagd.di.*
import fake.FakeTypedMapService
import fake.FakeTypedService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.lang.UnsupportedOperationException

@RunWith(MockitoJUnitRunner::class)
class KeyTest {

    @Test
    fun `given two keys with same key's value then verify they are equal`() {
        val key1: Key<Service> = Key("ping")
        val key2: Key<Service> = Key("ping")
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two keys with different key's value then verify they are not equal`() {
        val key1: Key<Service> = Key("ping")
        val key2: Key<Service> = Key("pong")
        assert(key1 != key2)
        assert(key1.hashCode() != key2.hashCode())
    }

    @Test
    fun `given two keys in which the second key is derived from first key then verify they are referentially same`() {
        val key1: Key<Service> = Key("ping")
        val key2: Key<Service> = key1

        assert(key1 === key2)
    }

    @Test
    fun `given two inline keys with empty key's value then verify they are same`() {
        val key1: Keyable<Service> = key()
        val key2: Keyable<Service> = key()
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two inline keys with same key's value then verify they are same`() {
        val key1: Keyable<Service> = key("ping")
        val key2: Keyable<Service> = key("ping")
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two inline keys with different key's value then verify they are not same`() {
        val key1: Keyable<Service> = key("ping")
        val key2: Keyable<Service> = key("pong")
        assert(key1 != key2)
        assert(key1.hashCode() != key2.hashCode())
    }

    @Test
    fun `given two inline keys with same typed class values then verify they are same`() {
        val key1: Keyable<FakeTypedService<String>> = key(typeOf<String>())
        val key2: Keyable<FakeTypedService<String>> = key(typeOf<String>())
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two inline keys with different typed class values then verify they are not same`() {
        val key1: Keyable<FakeTypedService<String>> = key(typeOf<String>())
        val key2: Keyable<FakeTypedService<Float>> = key(typeOf<Float>())
        assert(key1 != key2)
        assert(key1.hashCode() != key2.hashCode())
    }

    @Test
    fun `given two inline keys with typed and two parameters then verify they are same`() {
        val key1: Keyable<FakeTypedService<String>> = key2<FakeTypedService<String>, String>()
        val key2: Keyable<FakeTypedService<String>> = key2<FakeTypedService<String>, String>()
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two inline keys with typed and two different parameters then verify they are not same`() {
        val key1: Keyable<FakeTypedService<String>> = key2<FakeTypedService<String>, String>()
        val key2: Keyable<FakeTypedService<Float>> = key2<FakeTypedService<Float>, Float>()
        assert(key1 != key2)
        assert(key1.hashCode() != key2.hashCode())
    }

    @Test
    fun `given two inline keys with typed and three parameters then verify they are same`() {
        val key1: Keyable<FakeTypedMapService<String, Int>> =
            key3<FakeTypedMapService<String, Int>, String, Int>()
        val key2: Keyable<FakeTypedMapService<String, Int>> =
            key3<FakeTypedMapService<String, Int>, String, Int>()
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two inline keys with typed and three different parameters then verify they are not same`() {
        val key1: Keyable<FakeTypedMapService<String, Int>> =
            key3<FakeTypedMapService<String, Int>, String, Int>()
        val key2: Keyable<FakeTypedMapService<Int, String>> =
            key3<FakeTypedMapService<Int, String>, Int, String>()
        assert(key1 != key2)
        assert(key1.hashCode() != key2.hashCode())
    }
}