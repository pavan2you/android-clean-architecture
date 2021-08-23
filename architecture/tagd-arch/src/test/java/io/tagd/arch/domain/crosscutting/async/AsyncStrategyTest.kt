package io.tagd.arch.domain.crosscutting.async

import io.tagd.arch.fake.FakeAsyncStrategy
import io.tagd.arch.fake.FakeInjector
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AsyncStrategyTest {

    private lateinit var asyncStrategy: AsyncStrategy

    @Before
    fun setup() {
        FakeInjector.inject()
        asyncStrategy = FakeAsyncStrategy()
    }

    @After
    fun tearDown() {
        FakeInjector.release()
    }

    @Test
    fun `verify execute calls the work`() {
        var executed = false
        asyncStrategy.execute {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify execute calls the work with context`() {
        var executed = false
        asyncStrategy.execute("test") {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify cancel is successful`() {
        val result = asyncStrategy.cancel("test")
        assert(result)
    }

    @Test
    fun `verify release is successful`() {
        asyncStrategy.release()
        assert((asyncStrategy as FakeAsyncStrategy).released)
    }

    @Test
    fun `verify computation work without context`() {
        var executed = false
        compute {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify computation work with context`() {
        var executed = false
        compute("test") {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify presentation work without context`() {
        var executed = false
        present {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify presentation work with context`() {
        var executed = false
        present("test") {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify networkIO work without context`() {
        var executed = false
        networkIO {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify networkIO work with context`() {
        var executed = false
        networkIO("test") {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify diskIO work without context`() {
        var executed = false
        diskIO {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify diskIO work with context`() {
        var executed = false
        diskIO("test") {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify daoCrud work without context`() {
        var executed = false
        daoCrud {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify daoCrud work with context`() {
        var executed = false
        daoCrud("test") {
            executed = true
        }
        assert(executed)
    }
}