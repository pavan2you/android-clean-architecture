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

package io.tagd.arch.data.dao

import io.tagd.core.LayerSuperType
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.Global
import io.tagd.di.Keyable
import io.tagd.di.Scopable
import io.tagd.di.key

interface DataAccessObject : LayerSuperType, Service {

    /**
     * The [DataAccessObject.Factory] enables the DI frameworks and / or application logic to easily
     * create / get any [DataAccessObject]
     */
    companion object Factory {

        inline fun <reified S : DataAccessObject> dao(
            scope: Scopable = Global,
            key: Keyable<S>? = null
        ): S? {

            return scope.get<DataAccessObject, S>(key ?: key())
        }

        inline fun <reified S : DataAccessObject> createDao(
            scope: Scopable = Global,
            key: Keyable<S>? = null,
            state: State? = null
        ): S {

            return scope.create(key ?: key(), state)
        }
    }
}

abstract class AbstractDao : DataAccessObject {

    override fun release() {
    }
}