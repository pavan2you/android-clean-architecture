package io.tagd.app

import android.content.Context
import io.tagd.arch.data.dao.DataAccessObject
import io.tagd.arch.data.gateway.Gateway
import io.tagd.arch.data.repo.Repository
import io.tagd.di.*

class Injector {

    fun setup(context: Context) {
        scope("application") {

            //Infra layer
            layer<Infra> {
                bind(key(), InfraService(context))
            }

            //Infra mix layer
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
        }
    }
}