package io.tagd.droid.crosscutting

import androidx.annotation.VisibleForTesting
import io.tagd.androidx.coroutines.Dispatchers
import io.tagd.arch.domain.crosscutting.async.*
import io.tagd.javax.lang.ref.ComparableWeakReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.coroutines.CoroutineContext

open class CoroutineStrategy(
    protected val coroutineContext: CoroutineContext = Dispatchers.Main
) : AsyncStrategy {

    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(job + coroutineContext)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val monitor = ConcurrentHashMap<ComparableWeakReference<out Any>, CopyOnWriteArrayList<Job>>()

    override fun execute(context: Any?, work: () -> Unit) {
        executeCoroutine(context, work)
    }

    protected open fun executeCoroutine(context: Any?, work: () -> Unit) {
        val job = scope.launch {
            work.invoke()
        }
        monitorJob(context, job)
    }

    private fun monitorJob(context: Any?, job: Job) {
        val key = ComparableWeakReference(context ?: "global")
        var contextJobs = monitor[key]
        if (contextJobs == null) {
            contextJobs = CopyOnWriteArrayList()
            monitor[key] = contextJobs
        }
        contextJobs.add(job)
    }

    override fun cancel(context: Any?): Boolean {
        val key = ComparableWeakReference(context ?: "global")
        val contextJobs = monitor[key]
        contextJobs?.let {
            var i = 0
            // Using while loop to fix ConcurrentModificationException
            while (i < contextJobs.size) {
                val job = it[i]
                if (job.isActive) {
                    job.cancel()
                }
                i++
            }
        }
        contextJobs?.clear()
        return true
    }

    override fun release() {
        monitor.keys.forEach {
            cancel(it)
        }
        monitor.clear()

        job.cancel()
    }
}

class CoroutinePresentationStrategy : CoroutineStrategy(coroutineContext = Dispatchers.Main),
    PresentationStrategy

class CoroutineComputationStrategy : CoroutineStrategy(coroutineContext = Dispatchers.Default),
    ComputationStrategy

class CoroutineNetworkStrategy : CoroutineStrategy(coroutineContext = Dispatchers.IO),
    NetworkIOStrategy

class CoroutineDiskStrategy : CoroutineStrategy(coroutineContext = Dispatchers.IO),
    DiskIOStrategy

class CoroutineDaoStrategy : CoroutineStrategy(coroutineContext = Dispatchers.IO), DaoStrategy
