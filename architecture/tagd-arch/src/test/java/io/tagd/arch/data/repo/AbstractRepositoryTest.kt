package io.tagd.arch.data.repo

import com.nhaarman.mockito_kotlin.spy
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AbstractRepositoryTest {

    private val repository: AbstractRepository = spy()

    @Test
    fun `given a Repository then verify it is not null`() {
        Assert.assertNotNull(repository)
    }

    @Test
    fun `given release is called then verify it is handled`() {
        var called = false
        Mockito.`when`(repository.release()).thenAnswer {
            called = true
            return@thenAnswer Unit
        }

        repository.release()

        assert(called)
    }
}