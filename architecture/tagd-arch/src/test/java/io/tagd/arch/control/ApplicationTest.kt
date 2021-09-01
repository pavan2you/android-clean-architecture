package io.tagd.arch.control

import io.tagd.arch.fake.FakeApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ApplicationTest {

    private val application: IApplication = FakeApplication()

    @Test
    fun `given cancel is called and verify controller is not null`() {
        application as FakeApplication
        assert(application.controller<FakeApplication>() != null)
    }

    @Test
    fun `verify when release is called controller is cleared`() {
        application.release()

        assert(application.controller<FakeApplication>() == null)
    }
}