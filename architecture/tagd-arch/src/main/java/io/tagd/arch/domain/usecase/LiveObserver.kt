package io.tagd.arch.domain.usecase

import io.tagd.core.annotation.VisibleForTesting

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
class LiveObserver<T>(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    override val args: Args? = null,

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val invalidating: Callback<Unit>? = null,

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    override val success: Callback<T>? = null,

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    override val failure: Callback<Throwable>? = null
) : ResultObserver<T>(args, success, failure), LiveData.Observer<T> {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var resultVersion = NO_DATA_VERSION

    override fun onInvalidating() {
        invalidating?.invoke(Unit)
    }

    override fun onChange(value: T, version: Int) {
        if (resultVersion < version) {
            resultVersion = version
            setValue(value)
        }
    }

    override fun setValue(value: T) {
        if (args == null || args.observe) {
            result = value
        }
        success?.invoke(value)
    }

    override fun onError(error: Throwable) {
        setError(error)
    }

    companion object {
        private const val NO_DATA_VERSION = -1
    }
}