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

package io.tagd.arch.data.dao

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import io.tagd.di.Global
import io.tagd.di.layer
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataAccessObjectTest {

    private val dao: DataAccessObject = spy()

    @Test
    fun `given a Dao then verify it is not null`() {
        Assert.assertNotNull(dao)
    }

    @Test
    fun `verify dao access for bounded service`() {
        with(Global) {
            layer<DataAccessObject> {
                bind<DataAccessObject>().toInstance(mock())
            }
        }

        val service = DataAccessObject.dao<DataAccessObject>()
        assert(service != null)
    }

    @Test
    fun `verify createDataAccessObject access for bounded service`() {
        val service1 = FakeDataAccessObject()
        with(Global) {
            layer<DataAccessObject> {
                bind<FakeDataAccessObject>().toCreator { service1 }
            }
        }

        val service2 = DataAccessObject.createDao<FakeDataAccessObject>()
        assert(service1 === service2)
    }

    class FakeDataAccessObject : DataAccessObject {
        override fun release() {
        }
    }
}