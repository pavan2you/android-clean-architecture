package io.tagd.arch.data.repo

import com.nhaarman.mockito_kotlin.spy
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    private val repository: Repository = spy()

    @Test
    fun `given a Repository then verify it is not null`() {
        Assert.assertNotNull(repository)
    }
}