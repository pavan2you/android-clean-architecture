package io.tagd.arch.data.dao

import io.tagd.core.Service

interface DataAccessObject : Service

abstract class AbstractDao : DataAccessObject {

    override fun release() {
    }
}