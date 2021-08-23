package io.tagd.arch.fake

import io.tagd.arch.domain.crosscutting.CrossCutting
import io.tagd.arch.domain.crosscutting.async.*
import io.tagd.core.Releasable
import io.tagd.di.Global
import io.tagd.di.layer

class FakeInjector : Releasable {

    fun inject() {
        with(Global) {
            injectCrossCuttings()
        }
    }

    private fun Global.injectCrossCuttings() {
        // global - cross cuttings
        layer<CrossCutting> {

            //platform
            val testStrategy = FakeAsyncStrategy()
            bind<ComputationStrategy>().toInstance(testStrategy)
            bind<PresentationStrategy>().toInstance(testStrategy)
            bind<NetworkIOStrategy>().toInstance(testStrategy)
            bind<DiskIOStrategy>().toInstance(testStrategy)
            bind<DaoStrategy>().toInstance(testStrategy)
        }
    }

    override fun release() {
        Global.reset()
    }

    companion object {

        private var active: FakeInjector? = null

        fun inject(): FakeInjector {
            release()
            active = FakeInjector().apply { inject() }
            return active!!
        }

        fun release() {
            active?.release()
        }
    }
}