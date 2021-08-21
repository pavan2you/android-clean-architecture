package io.tagd.arch.domain.usecase

import io.tagd.core.annotation.VisibleForTesting
import java.lang.ref.WeakReference

@Suppress("LeakingThis")
abstract class UseCase<E, T> : Command<E, T> {

    private val defaultArgs = argsOf()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val executionMonitor = ExecutionMonitor(WeakReference(this))

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    open fun resolveArgs(args: Args?) = args ?: defaultArgs

    protected fun resolveArgsWithMeta(args: Args?): Args {
        return resolveArgs(args).also {
            fillMetaProperties(it)
        }
    }

    private fun fillMetaProperties(args: Args) {
        args.put(ARG_USECASE, this)
    }

    override fun execute(args: Args?, success: Callback<T>?, failure: Callback<Throwable>?): E {
        return execute(args, null, success, failure)
    }

    protected abstract fun execute(
        args: Args?,
        triggering: Callback<Unit>?,
        success: Callback<T>?,
        failure: Callback<Throwable>?
    ): E

    protected fun execute(
        arguments: Args,
        success: ((T) -> Unit)?,
        existing: ResultObserver<T>?
    ) {
        when {
            existing == null -> {
                executedBy(arguments, ExecutionMonitor.ExecutionResolution.TRIGGERING)
                trigger(arguments)
            }
            existing.result != null -> {
                executedBy(arguments, ExecutionMonitor.ExecutionResolution.CACHED_RESULT)
                success?.invoke(existing.result!!)
            }
            else -> {
                executedBy(arguments, ExecutionMonitor.ExecutionResolution.IGNORING)
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    open fun invalidate(arguments: Args) {
        executedBy(arguments, ExecutionMonitor.ExecutionResolution.INVALIDATING)
        trigger(arguments)
    }

    private fun executedBy(args: Args, value: ExecutionMonitor.ExecutionResolution) {
        executionMonitor.executedBy(args, value)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    abstract fun trigger(args: Args)

    abstract fun flush(args: Args)

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    open fun setValue(args: Args?, value : T) {
        executionMonitor.removeExecutionMonitorIfNotCacheable(args)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    open fun setError(args: Args?, throwable: Throwable) {
        executionMonitor.removeExecutionMonitor(args)
    }

    abstract fun isActive(context: Any? = null): Boolean

    override fun release() {
        executionMonitor.release()
    }

    companion object {
        const val ARG_USECASE = "usecase"
    }
}