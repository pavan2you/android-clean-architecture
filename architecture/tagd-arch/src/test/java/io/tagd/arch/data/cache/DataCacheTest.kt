package io.tagd.arch.data.cache

import com.nhaarman.mockito_kotlin.spy
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataCacheTest {

    private val dao: DataCache = spy()

    @Test
    fun `given a DataCache then verify it is not null`() {
        Assert.assertNotNull(dao)
    }
}