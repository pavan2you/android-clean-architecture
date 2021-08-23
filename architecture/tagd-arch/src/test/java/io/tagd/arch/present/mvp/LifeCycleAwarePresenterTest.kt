package io.tagd.arch.present.mvp

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LifeCycleAwarePresenterTest {

    private val view: PresentableView = mock()
    private val presenter = spy(LifeCycleAwarePresenter(view))

    @Test
    fun `verify view is not null`() {
        assert(presenter.view != null)
    }

    @Test
    fun `verify view is released`() {
        presenter.release()
        assert(presenter.view == null)
    }

    @Test
    fun `verify OnCreate is called`() {
        var called = false
        `when`(presenter.onCreate()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onCreate()

        assert(called)
    }

    @Test
    fun `verify OnStart is called`() {
        var called = false
        `when`(presenter.onStart()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onStart()

        assert(called)
    }

    @Test
    fun `verify OnResume is called`() {
        var called = false
        `when`(presenter.onResume()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onResume()

        assert(called)
    }

    @Test
    fun `verify OnPause is called`() {
        var called = false
        `when`(presenter.onPause()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onPause()

        assert(called)
    }

    @Test
    fun `verify OnStop is called`() {
        var called = false
        `when`(presenter.onStop()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onStop()

        assert(called)
    }

    @Test
    fun `verify OnDestroy is called`() {
        var called = false
        `when`(presenter.onDestroy()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onDestroy()

        assert(called)
    }

    @Test
    fun `verify onBackPressed is called`() {
        var called = false
        `when`(presenter.onBackPressed()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onBackPressed()

        assert(called)
    }

    @Test
    fun `given canHandleBackPress is null then verify result is false`() {
        presenter.canHandleBackPress = null
        val result = presenter.canHandleBackPress()
        assert(!result)
    }

    @Test
    fun `given canHandleBackPress is non null then verify result is value`() {
        val value = presenter.canHandleBackPress
        val result = presenter.canHandleBackPress()
        assert(result == value)
    }
}