package io.tagd.arch.domain.usecase

import io.tagd.core.Releasable
import io.tagd.core.annotation.VisibleForTesting
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
class ExecutionMonitor<E, T>(private val useCaseRef: WeakReference<UseCase<E, T>>) : Releasable {

    private val useCase
        get() = useCaseRef.get()

    private val monitors = ConcurrentHashMap<Args?, ExecutionResolution>()

    fun isLastExecutionIgnored(args: Args?): Boolean {
        return resolutionFor(args) == ExecutionResolution.IGNORING
    }

    fun isLastExecutionFromCache(args: Args?): Boolean {
        return resolutionFor(args) == ExecutionResolution.CACHED_RESULT
    }

    fun isLastExecutionFromTrigger(args: Args?): Boolean {
        return resolutionFor(args) == ExecutionResolution.TRIGGERING
    }

    fun isLastExecutionFromInvalidation(args: Args?): Boolean {
        return resolutionFor(args) == ExecutionResolution.INVALIDATING
    }

    fun isHavingExecutionMonitor(args: Args?): Boolean {
        return resolutionFor(args) != null
    }

    fun executedBy(args: Args, value: ExecutionResolution) {
        monitors[args] = value
    }

    private fun resolutionFor(args: Args?): ExecutionResolution? {
        val arguments = useCase?.resolveArgs(args)
        return monitors[arguments]
    }

    fun removeExecutionMonitorIfNotCacheable(args: Args?) {
        if (args?.observe != true) {
            removeExecutionMonitor(args)
        }
    }

    fun removeExecutionMonitor(args: Args?) {
        useCase?.resolveArgs(args)?.let { remove(it) }
    }

    fun remove(args: Args) {
        monitors.remove(args)
    }

    override fun release() {
        monitors.clear()
        useCaseRef.clear()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    enum class ExecutionResolution {
        TRIGGERING, IGNORING, CACHED_RESULT, INVALIDATING
    }
}