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

package io.tagd.arch.domain.service

import com.nhaarman.mockito_kotlin.mock
import io.tagd.arch.fake.FakeInjector
import io.tagd.di.Global
import io.tagd.di.layer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DomainServiceTest {

    @Before
    fun setup() {
        FakeInjector.inject()
    }

    @After
    fun tearDown() {
        FakeInjector.release()
    }

    @Test
    fun `verify domainService access for bounded service`() {
        with(Global) {
            layer<DomainService> {
                bind<DomainService>().toInstance(mock())
            }
        }

        val service = DomainService.domainService<DomainService>()
        assert(service != null)
    }

    @Test
    fun `verify createDomainService access for bounded service`() {
        val service1 = FakeDomainService()
        with(Global) {
            layer<DomainService> {
                bind<FakeDomainService>().toCreator { service1 }
            }
        }

        val service2 = DomainService.createDomainService<FakeDomainService>()
        assert(service1 === service2)
    }

    class FakeDomainService : DomainService {
        override fun release() {
        }
    }
}