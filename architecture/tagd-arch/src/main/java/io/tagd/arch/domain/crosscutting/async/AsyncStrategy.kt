package io.tagd.arch.domain.crosscutting.async

import io.tagd.arch.access.crosscutting
import io.tagd.arch.domain.crosscutting.CrossCutting
import io.tagd.core.Cancellable

interface AsyncStrategy : CrossCutting, Cancellable {

    fun execute(context: Any? = null, work: () -> Unit)
}

interface PresentationStrategy : AsyncStrategy

interface ComputationStrategy : AsyncStrategy

interface NetworkIOStrategy : AsyncStrategy

interface DiskIOStrategy : AsyncStrategy

interface DaoStrategy : AsyncStrategy

fun compute(context: Any? = null, computation: () -> Unit) {
    val strategy = crosscutting<ComputationStrategy>()
    strategy?.execute(context, computation)
}

fun present(context: Any? = null, presentation: () -> Unit) {
    val strategy = crosscutting<PresentationStrategy>()
    strategy?.execute(context, presentation)
}

fun networkIO(context: Any? = null, api: () -> Unit) {
    val strategy = crosscutting<NetworkIOStrategy>()
    strategy?.execute(context, api)
}

fun diskIO(context: Any? = null, operation: () -> Unit) {
    val strategy = crosscutting<DiskIOStrategy>()
    strategy?.execute(context, operation)
}

fun daoCrud(context: Any? = null, crudOperation: () -> Unit) {
    val strategy = crosscutting<DaoStrategy>()
    strategy?.execute(context, crudOperation)
}

fun cancelAsync(context: Any) {
    cancelPresentations(context)
    cancelComputations(context)
    cancelNetworkIO(context)
    cancelDiskIO(context)
    cancelDaoCrud(context)
}

fun cancelPresentations(context: Any) {
    val strategy = crosscutting<PresentationStrategy>()
    strategy?.cancel(context)
}

fun cancelComputations(context: Any) {
    val strategy = crosscutting<ComputationStrategy>()
    strategy?.cancel(context)
}

fun cancelNetworkIO(context: Any) {
    val strategy = crosscutting<NetworkIOStrategy>()
    strategy?.cancel(context)
}

fun cancelDiskIO(context: Any) {
    val strategy = crosscutting<DiskIOStrategy>()
    strategy?.cancel(context)
}

fun cancelDaoCrud(context: Any) {
    val strategy = crosscutting<DaoStrategy>()
    strategy?.cancel(context)
}