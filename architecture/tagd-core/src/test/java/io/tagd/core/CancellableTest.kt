package io.tagd.core

import io.tagd.core.fake.FakeCancellable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CancellableTest {

    private val cancellable: Cancellable = FakeCancellable()

    @Test
    fun `given cancel is called and if cancellation is successful then verify result is true`() {
        cancellable as FakeCancellable
        assert(!cancellable.cancelled)
        cancellable.cancel()
        assert(cancellable.cancelled)
    }

    @Test
    fun `given cancel with context and if cancellation is successful then verify result is true`() {
        cancellable as FakeCancellable
        assert(!cancellable.cancelled)
        cancellable.cancel(context = "context")
        assert(cancellable.cancelled)
    }

    @Test
    fun `given cancel with invalid context then verify result is false`() {
        cancellable as FakeCancellable
        assert(!cancellable.cancelled)
        cancellable.cancel(context = "invalid")
        assert(!cancellable.cancelled)
    }

}