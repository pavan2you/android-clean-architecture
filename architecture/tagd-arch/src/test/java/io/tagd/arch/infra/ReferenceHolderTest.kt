package io.tagd.arch.infra

import com.nhaarman.mockito_kotlin.spy
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReferenceHolderTest {

    private val referenceHolder: ReferenceHolder<String> = spy(ReferenceHolder("hello"))

    @Test
    fun `given a Reference then verify it is not null`() {
        assertNotNull(referenceHolder)
    }

    @Test
    fun `given a Reference then verify value is not null`() {
        assertNotNull(referenceHolder.value)
    }

    @Test
    fun `given a Reference then verify value is returned is as expected`() {
        assert(referenceHolder.value == "hello")
    }

    @Test
    fun `given Release is called then verify it is handled`() {
        assertNotNull(referenceHolder.value)
        referenceHolder.release()
        assert(referenceHolder.value == null)
    }
}