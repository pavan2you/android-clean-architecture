package io.tagd.arch.data.cache

import com.nhaarman.mockito_kotlin.spy
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AbstractCacheTest {

    private val cache: AbstractCache<Any> = spy()

    @Test
    fun `given a DataCache then verify it is not null`() {
        Assert.assertNotNull(cache)
    }

    @Test
    fun `given release is called then verify it is handled`() {
        var called = false
        Mockito.`when`(cache.release()).thenAnswer {
            called = true
            return@thenAnswer Unit
        }

        cache.release()

        assert(called)
    }
}