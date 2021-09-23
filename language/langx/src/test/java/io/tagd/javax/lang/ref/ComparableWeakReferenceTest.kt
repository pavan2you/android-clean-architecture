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

package io.tagd.javax.lang.ref

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ComparableWeakReferenceTest {

    @Test
    fun `given two weak references are holding same value, then return they are equal`() {
        val stringRef1 = ComparableWeakReference("string")
        val stringRef2 = ComparableWeakReference<String>("string")

        assert(stringRef1 == stringRef2)
    }

    @Test
    fun `given two weak references are not holding same value, then return they are not equal`() {
        val stringRef1 = ComparableWeakReference("string1")
        val stringRef2 = ComparableWeakReference<String>("string2")

        assert(stringRef1 != stringRef2)
    }

    @Test
    fun `given a reference and weak reference are holding same value, then return they are equal`() {
        val stringRef1 = ComparableWeakReference("string")
        val stringRef2 = "string"

        assert(stringRef1.equals(stringRef2))
    }

    @Test
    fun `given two weak references are holding same value, then ensure their hashcodes are same`() {
        val stringRef1 = ComparableWeakReference("string")
        val stringRef2 = ComparableWeakReference<String>("string")

        assert(stringRef1.hashCode() == stringRef2.hashCode())
    }
}