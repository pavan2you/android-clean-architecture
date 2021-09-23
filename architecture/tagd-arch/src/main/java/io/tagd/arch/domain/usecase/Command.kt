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

package io.tagd.arch.domain.usecase

import io.tagd.core.Cancellable
import io.tagd.core.LayerSuperType
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.Global
import io.tagd.di.Keyable
import io.tagd.di.Scopable
import io.tagd.di.key

typealias Callback<T> = (T) -> Unit

interface Command<EXECUTE, T> : LayerSuperType, Service, Cancellable {

    fun execute(
        args: Args? = null,
        success: Callback<T>? = null,
        failure: Callback<Throwable>? = null
    ): EXECUTE

    fun lastResult(args: Args? = null): T?

    /**
     * The [Command.Factory] enables the DI frameworks and / or application logic to easily
     * create / get any [Command]
     */
    companion object Factory {

        inline fun <reified S : Command<*, *>> usecase(
            scope: Scopable = Global,
            key: Keyable<S>? = null
        ): S? {

            return scope.get<Command<*, *>, S>(key ?: key())
        }

        inline fun <reified S : Command<*, *>> createUsecase(
            scope: Scopable = Global,
            key: Keyable<S>? = null,
            state: State? = null
        ): S {

            return scope.create(key ?: key(), state)
        }
    }
}