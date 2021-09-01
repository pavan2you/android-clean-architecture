package io.tagd.arch.control

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LifeCycleAwareApplicationControllerTest {

    private val app: IApplication = mock()
    private val controller = Mockito.spy(LifeCycleAwareApplicationController(app))

    @Test
    fun `verify app is not null when controller initialized`() {
        assert(controller.app != null)
    }

    @Test
    fun `verify when release is called app is cleared`() {
        controller.release()

        assert(controller.app == null)
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
    fun `verify onLaunch is called`() {
        var called = false
        Mockito.`when`(controller.onLaunch()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        controller.onLaunch()

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
    fun `verify onForeground is called`() {
        var called = false
        Mockito.`when`(controller.onForeground()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        controller.onForeground()

        assert(called)
    }

    @Test
    fun `verify onBackground is called`() {
        var called = false
        Mockito.`when`(controller.onBackground()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        controller.onBackground()

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