package io.tagd.arch.data.dao

import com.nhaarman.mockito_kotlin.spy
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AbstractDaoTest {

    private val dao: AbstractDao = spy()

    @Test
    fun `given a Dao then verify it is not null`() {
        Assert.assertNotNull(dao)
    }

    @Test
    fun `given release is called then verify it is handled`() {
        var called = false
        Mockito.`when`(dao.release()).thenAnswer {
            called = true
            return@thenAnswer Unit
        }

        dao.release()

        assert(called)
    }
}