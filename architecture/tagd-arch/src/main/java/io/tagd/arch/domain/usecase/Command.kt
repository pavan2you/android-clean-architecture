package io.tagd.arch.domain.usecase

import io.tagd.core.Cancellable
import io.tagd.core.Service

typealias Callback<T> = (T) -> Unit

interface Command<EXECUTE, T> : Cancellable, Service {

    fun execute(
        args: Args? = null,
        success: Callback<T>? = null,
        failure: Callback<Throwable>? = null
    ): EXECUTE

    fun lastResult(args: Args? = null): T?
}