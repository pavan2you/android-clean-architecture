package io.tagd.arch.data.gateway

import io.tagd.core.Service

interface Gateway : Service

abstract class AbstractGateway : Gateway {

    override fun release() {
    }
}