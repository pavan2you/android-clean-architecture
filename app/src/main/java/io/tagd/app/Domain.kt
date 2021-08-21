package io.tagd.app

import io.tagd.arch.data.dao.DataAccessObject
import io.tagd.arch.data.gateway.Gateway
import io.tagd.arch.data.repo.Repository
import io.tagd.core.Service
import java.lang.ref.WeakReference

interface Infra : Service
interface TypedService<T> : Service

class SimpleGateway : Gateway {
    override fun release() {
    }
}

class SimpleDao : DataAccessObject {
    override fun release() {
    }
}

class SimpleRepo : Repository {
    override fun release() {
    }
}

class SimpleRepo2 : Repository {
    override fun release() {
    }
}

class InfraService<T>(infra: T) : Infra {

    private var infraReference = WeakReference(infra)

    override fun release() {
        infraReference.clear()
    }

    override fun toString(): String {
        return "InfraService(infraObject=${infraReference.get()})"
    }
}

class SimpleTypedService<T> : TypedService<T> {
    override fun release() {
    }
}

class SomeObject