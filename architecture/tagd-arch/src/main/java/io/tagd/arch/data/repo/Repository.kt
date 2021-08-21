package io.tagd.arch.data.repo

import io.tagd.core.Service

interface Repository : Service

abstract class AbstractRepository : Repository {

    override fun release() {
    }
}