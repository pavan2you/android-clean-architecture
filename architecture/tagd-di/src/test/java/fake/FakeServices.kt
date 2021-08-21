package fake

import io.tagd.core.Service

class FakeService : Service {
    override fun release() {
    }
}

class FakeTypedService<T> : Service {
    override fun release() {
    }
}

class FakeTypedMapService<T, S> : Service {
    override fun release() {
    }
}