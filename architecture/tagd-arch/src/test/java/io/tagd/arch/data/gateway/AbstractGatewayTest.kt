package io.tagd.arch.data.gateway

import com.nhaarman.mockito_kotlin.spy
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AbstractGatewayTest {

    private val gateway: AbstractGateway = spy()

    @Test
    fun `given a Gateway then verify it is not null`() {
        Assert.assertNotNull(gateway)
    }

    @Test
    fun `given release is called then verify it is handled`() {
        var called = false
        Mockito.`when`(gateway.release()).thenAnswer {
            called = true
            return@thenAnswer Unit
        }

        gateway.release()

        assert(called)
    }
}