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

package io.tagd.javax.util.collection

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CopyOnWriteArrayList

@RunWith(MockitoJUnitRunner::class)
class CopyOnWriteArrayListExtTest {

    @Test
    fun `given removeAllByFilter is called then verify filtered results are removed`() {
        val collection = CopyOnWriteArrayList<String>()
        collection.add("one")
        collection.add("two")
        collection.add("three")
        collection.add("four")
        collection.add("five")

        collection.removeAllByFilter {
            it == "one" || it == "two"
        }

        assert(!collection.contains("one"))
        assert(!collection.contains("two"))
    }
}