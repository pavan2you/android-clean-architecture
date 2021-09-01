package io.tagd.arch.control.mcc

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LifeCycleAwareControllerTest {

    private val controllable: Controllable = mock()
    private val controller = Mockito.spy(LifeCycleAwareController(controllable))

    @Test
    fun `verify controllable is not null when controller initialized`() {
        assert(controller.controllable != null)
    }

    @Test
    fun `verify when release is called controllable is cleared`() {
        controller.release()

        assert(controller.controllable == null)
    }

    @Test
    fun `verify onCreate is called`() {
        var called = false
        Mockito.`when`(controller.onCreate()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        controller.onCreate()

        assert(called)
    }

    @Test
    fun `verify onStart is called`() {
        var called = false
        Mockito.`when`(controller.onStart()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        controller.onStart()

        assert(called)
    }

    @Test
    fun `verify onAwaiting is called`() {
        var called = false
        Mockito.`when`(controller.onAwaiting()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        controller.onAwaiting()

        assert(called)
    }

    @Test
    fun `verify onReady is called`() {
        var called = false
        Mockito.`when`(controller.onReady()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        controller.onReady()

        assert(called)
    }

    @Test
    fun `verify onStop is called`() {
        var called = false
        Mockito.`when`(controller.onStop()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        controller.onStop()

        assert(called)
    }

    @Test
    fun `verify onDestroy is called`() {
        var called = false
        Mockito.`when`(controller.onDestroy()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        controller.onDestroy()

        assert(called)
    }
}