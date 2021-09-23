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

import android.content.Context
import io.tagd.androidx.coroutines.Dispatchers
import io.tagd.arch.data.dao.DataAccessObject
import io.tagd.arch.data.gateway.Gateway
import io.tagd.arch.data.repo.Repository
import io.tagd.arch.domain.crosscutting.Crosscutting
import io.tagd.arch.domain.crosscutting.async.*
import io.tagd.arch.infra.InfraService
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.di.*
import io.tagd.droid.crosscutting.*
import io.tagd.droid.launch.Injector

class AppInjector(application: SampleApplication) : Injector(application) {

    override fun inject() {
        super.inject()

        scope("application") {

            //Infra layer
            layer<InfraService> {
                bind(key(), InfraTypedService(app))

                bind(
                    key2<ReferenceHolder<Context>, Context>(),
                    ReferenceHolder(app!!)
                )

                bind(
                    key2<ReferenceHolder<Dispatchers>, Dispatchers>(),
                    ReferenceHolder(provideDispatchers())
                )
            }

            //typed services layer
            layer<TypedService<*>> {
                bind(key2<SimpleTypedService<Context>, Context>(),
                    SimpleTypedService()
                )
                bind(
                    key<SimpleTypedService<SomeObject>>(
                        typeOf<SomeObject>()
                    ),
                    SimpleTypedService()
                )
            }

            //Repo layer
            layer<Repository> {
                bind<SimpleRepo>().toInstance(
                    SimpleRepo()
                )
                bind<SimpleRepo2>().toInstance(
                    SimpleRepo2()
                )
                bind(key("Repo2Obj2"), SimpleRepo2())
                bind(key("Repo2Obj3"), SimpleRepo2())
            }

            //Dao layer
            layer<DataAccessObject> {
                bind<SimpleDao>().toInstance(
                    SimpleDao()
                )
            }

            //Gateway layer
            layer<Gateway> {
                bind<SimpleGateway>().toInstance(
                    SimpleGateway()
                )
            }

            // global - cross cuttings
            layer<Crosscutting> {
                bind<ComputationStrategy>().toInstance(CoroutineComputationStrategy())
                bind<PresentationStrategy>().toInstance(CoroutinePresentationStrategy())
                bind<NetworkIOStrategy>().toInstance(CoroutineNetworkStrategy())
                bind<DiskIOStrategy>().toInstance(CoroutineDiskStrategy())
                bind<DaoStrategy>().toInstance(CoroutineDaoStrategy())
            }
        }
    }

    private fun provideDispatchers(): Dispatchers {
        Dispatchers.set(
            Dispatchers.Builder()
                .Main(kotlinx.coroutines.Dispatchers.Main.immediate)
                .Default(kotlinx.coroutines.Dispatchers.Default)
                .IO(kotlinx.coroutines.Dispatchers.IO)
                .Unconfined(kotlinx.coroutines.Dispatchers.Unconfined)
                .build()
        )
        return Dispatchers.get()
    }
}