/*
 * Copyright (C) 2021 The TagD Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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