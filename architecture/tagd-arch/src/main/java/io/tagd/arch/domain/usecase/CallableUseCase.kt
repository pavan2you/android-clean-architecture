package io.tagd.arch.domain.usecase

import io.javax.util.collection.removeAllByFilter
import io.tagd.core.annotation.VisibleForTesting
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

abstract class CallableUseCase<T> : UseCase<Unit, T>() {

    private var cancelled: AtomicBoolean = AtomicBoolean(false)
    private var cancelledContexts = CopyOnWriteArrayList<String>()

    private val observers = CopyOnWriteArrayList<ResultObserver<T>?>()

    override fun execute(
        args: Args?,
        triggering: Callback<Unit>?,
        success: Callback<T>?,
        failure: Callback<Throwable>?
    ) {
        val arguments = resolveArgsWithMeta(args)
        cancelledContexts.remove(arguments.context as? String)
        cancelled.set(false)

        val observer: ResultObserver<T>? = ResultObserver(arguments, success, failure)
        val existing = putIfAbsent(observer)

        triggering?.invoke(Unit)
        execute(arguments, success, existing)
    }

    private fun putIfAbsent(observer: ResultObserver<T>?): ResultObserver<T>? {
        val existing = observers.lastOrNull {
            it == observer
        }
        if (existing == null) {
            observers.add(observer)
        }
        return existing
    }

    override fun invalidate(arguments: Args) {
        throw IllegalAccessException(
            "Callable is only for one shot usage, so not possible to invalidate"
        )
    }

    override fun flush(arguments: Args) {
        removeObserver(arguments)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    override fun setValue(args: Args?, value : T) {
        if (!cancelled.get()) {
            val arguments = resolveArgs(args)
            notifyValue(arguments, value)
            removeAllButRecentResult(arguments)
        }
        super.setValue(args, value)
    }

    private fun notifyValue(arguments: Args, value: T) {
        observers.filter {
            //filter the only result awaiting observers.
            it?.args == arguments && it.result == null
        }.forEach {
            it?.setValue(value)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    override fun setError(args: Args?, throwable: Throwable) {
        if (!cancelled.get()) {
            val arguments = resolveArgs(args)
            notifyError(arguments, throwable)
            removeObserver(arguments)
        }
        super.setError(args, throwable)
    }

    private fun notifyError(arguments: Args, throwable: Throwable) {
        observers.filter {
            //filter the only result awaiting observers.
            it?.args == arguments && it.result == null
        }.forEach {
            it?.setError(throwable)
        }
    }

    private fun removeAllButRecentResult(arguments: Args) {
        val mostRecent = observers.lastOrNull {
            it?.args == arguments && it.result != null
        }
        observers.removeAllByFilter { it?.args == arguments }
        if (arguments.observe && mostRecent != null) {
            observers.add(mostRecent)
        }
    }

    private fun removeObserver(arguments: Args?) {
        observers.removeAllByFilter { it?.args == arguments }
    }

    override fun lastResult(args: Args?): T? {
        val arguments = resolveArgs(args)
        return observers.firstOrNull {
            it?.args == arguments
        }?.result
    }

    override fun cancel(context: Any?): Boolean {
        if (context == null) {
            cancelledContexts.clear()
            cancelled.set(true)
            observers.clear()
        } else {
            observers.removeAll {
                it?.args?.context == context
            }
        }
        return true
    }

    override fun isActive(context: Any?): Boolean {
        return if (context == null) {
            observers.size > 0
        } else {
            observers.any {
                it?.args?.context == context
            }
        }
    }

    override fun release() {
        cancelled.set(true)
        observers.clear()
    }
}