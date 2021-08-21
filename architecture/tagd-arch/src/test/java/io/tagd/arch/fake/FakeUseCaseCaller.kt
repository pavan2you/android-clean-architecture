package io.tagd.arch.fake

import io.tagd.arch.domain.usecase.Args
import io.tagd.arch.domain.usecase.Callback

class FakeUseCaseCaller<T>(val args: Args? = Args()) {

    var triggered: Boolean = false
    var result: T? = null
    var error: Throwable? = null

    val triggering: Callback<Unit> = {
        triggered = true
    }

    val success: Callback<T> = {
        result = it
    }

    val failure: Callback<Throwable> = {
        error = it
    }
}