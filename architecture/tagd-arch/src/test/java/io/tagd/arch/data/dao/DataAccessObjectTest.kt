package io.tagd.arch.data.dao

import com.nhaarman.mockito_kotlin.spy
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataAccessObjectTest {

    private val dao: DataAccessObject = spy()

    @Test
    fun `given a Dao then verify it is not null`() {
        Assert.assertNotNull(dao)
    }
}