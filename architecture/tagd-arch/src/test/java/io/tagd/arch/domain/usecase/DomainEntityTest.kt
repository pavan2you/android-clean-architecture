package io.tagd.arch.domain.usecase

import io.tagd.arch.domain.DomainEntity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DomainEntityTest {

    @Test
    fun `given a domain entity is created then verify it is not null`() {
        val domainEntity = DomainEntity()
        Assert.assertNotNull(domainEntity)
    }
}