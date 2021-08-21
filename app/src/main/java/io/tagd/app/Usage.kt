package io.tagd.app

import android.content.Context
import io.tagd.core.Releasable
import io.tagd.di.inject
import io.tagd.di.injectX
import io.tagd.di.key
import io.tagd.di.key2

class Usage : Releasable {

    private val simpleGateway by inject<SimpleGateway>()
    private val simpleRepo by inject<SimpleRepo>(scope = "application")
    private val simpleRepo2 by inject<SimpleRepo2>(scope = "application")
    private val simpleDao by inject<SimpleDao>(scope = "random")

    private val infraService by inject<InfraService<Context>>()

    private val typedContextService by inject(
        key = key2<SimpleTypedService<Context>, Context>()
    )

    private val typeSomeObjectService by inject(
        key = key2<SimpleTypedService<SomeObject>, SomeObject>()
    )

    private var simpleRepo2Obj2 by injectX<SimpleRepo2>(
        key = key("Repo2Obj2")
    )
    private var simpleRepo2Obj3 by injectX<SimpleRepo2>(
        key = key("Repo2Obj3")
    )

    fun use() {
        println("Gateway -> $simpleGateway")
        println("Repo -> $simpleRepo")
        println("Repo2 -> $simpleRepo2")
        println("Dao -> $simpleDao")
        println("Infra -> $infraService")
        println("typedOne -> $typedContextService")
        println("typedTwo -> $typeSomeObjectService")
        println("Repo2Obj2 -> $simpleRepo2Obj2")
        println("Repo2Obj3 -> $simpleRepo2Obj3")
    }

    override fun release() {
        simpleRepo2Obj2 = null
        simpleRepo2Obj3 = null
    }
}