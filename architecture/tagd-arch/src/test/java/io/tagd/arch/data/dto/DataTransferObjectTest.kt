package io.tagd.arch.data.dto

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataTransferObjectTest {

    @Test
    fun `given a DataTransferObject is created then verify it is not null`() {
        val dataTransferObject = DataTransferObject()
        Assert.assertNotNull(dataTransferObject)
        Assert.assertNotNull(dataTransferObject.crudOperation)
    }
}