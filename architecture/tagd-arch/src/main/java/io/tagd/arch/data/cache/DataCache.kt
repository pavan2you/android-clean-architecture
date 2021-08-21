package io.tagd.arch.data.cache

import io.tagd.core.Service

interface DataCache : Service

abstract class AbstractDataCache : DataCache {

    override fun release() {
    }
}