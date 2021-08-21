package io.tagd.arch.data

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataObjectTest {

    @Test
    fun `given a DataObject is created then verify it is not null`() {
        val dataObject = DataObject()
        Assert.assertNotNull(dataObject)
        Assert.assertNotNull(dataObject.crudOperation)
    }

    @Test
    fun `given initialize is called then verify object is having default state`() {
        val dataObject = DataObject()
        dataObject.initialize()
        assert(dataObject.crudOperation == DataObject.CrudOperation.CREATE)
        assert(dataObject.bindables.isEmpty())
    }
}