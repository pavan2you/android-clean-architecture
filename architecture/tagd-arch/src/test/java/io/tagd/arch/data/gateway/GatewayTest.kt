package io.tagd.arch.data.gateway

import com.nhaarman.mockito_kotlin.spy
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GatewayTest {

    private val gateway: Gateway = spy()

    @Test
    fun `given a Gateway then verify it is not null`() {
        Assert.assertNotNull(gateway)
    }
}