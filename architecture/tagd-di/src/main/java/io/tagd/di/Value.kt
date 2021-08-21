package io.tagd.di

import io.tagd.core.Service
import io.tagd.core.State

abstract class Value<T : Service> {

    abstract fun get(args: State? = null): T
}

class GetValue<T : Service>(private var value: T): Value<T>() {

    override fun get(args: State?): T {
        return value
    }
}

class CreateValue<T : Service>(private val creator: (State?) -> T): Value<T>() {

    override fun get(args: State?): T {
        return creator.invoke(args)
    }
}