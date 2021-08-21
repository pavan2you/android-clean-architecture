package io.tagd.arch.domain.usecase

import io.tagd.core.annotation.VisibleForTesting

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
open class ResultObserver<T>(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    open val args: Args? = null,

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    open val success: Callback<T>? = null,

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    open val failure: Callback<Throwable>? = null
) {

    var result: T? = null
        protected set

    open fun setValue(value: T) {
        result = value
        success?.invoke(value)
    }

    open fun setError(result: Throwable) {
        failure?.invoke(result)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResultObserver<*>

        if (args != other.args) return false
        if (success != other.success) return false
        if (failure != other.failure) return false
        return true
    }

    override fun hashCode(): Int {
        var result = args?.hashCode() ?: 0
        result = 31 * result + (success?.hashCode() ?: 0)
        result = 31 * result + (failure?.hashCode() ?: 0)
        return result
    }
}