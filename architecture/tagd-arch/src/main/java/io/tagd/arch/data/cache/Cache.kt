package io.tagd.arch.data.cache

import io.tagd.core.Service

interface Cache<T> : Service

abstract class AbstractCache<T> : Cache<T> {

    override fun release() {
    }
}