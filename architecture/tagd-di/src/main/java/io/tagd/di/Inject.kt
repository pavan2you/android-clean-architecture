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

package io.tagd.di

import io.tagd.core.Service
import io.tagd.di.Scope.Companion.GLOBAL_SCOPE

open class InjectProvider {

    open fun <T : Service> inject(scope: String, key: Keyable<T>): InjectDelegateProvider<T> {
        return InjectDelegateProvider(scope, key)
    }

    open fun <T : Service> injectX(
        scope: String,
        key: Keyable<T>
    ): NullableInjectDelegateProvider<T> {

        return NullableInjectDelegateProvider(scope, key)
    }

    companion object {
        var active: InjectProvider = InjectProvider()
    }
}

inline fun <reified T : Service> inject(
    scope: String = GLOBAL_SCOPE,
    key: Keyable<T> = Key(typeOf<T>())
): InjectDelegateProvider<T> {

    return InjectProvider.active.inject(scope, key)
}

inline fun <reified T : Service> injectX(
    scope: String = GLOBAL_SCOPE,
    key: Keyable<T> = Key(typeOf<T>())
): NullableInjectDelegateProvider<T> {

    return InjectProvider.active.injectX(scope, key)
}