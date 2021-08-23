package io.tagd.arch.control.mcc

import com.nhaarman.mockito_kotlin.mock
import org.junit.After
import org.junit.Before
import org.junit.Test

class LifeCycleAwareControllerTest {

    private lateinit var lifeCycleAwareController: LifeCycleAwareController<Controllable>

    @Before
    fun setUp() {
        val controllable: Controllable = mock()
        lifeCycleAwareController = LifeCycleAwareController(controllable)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `verify controllable is not null when controller initialized`() {
        assert(lifeCycleAwareController.controllable != null)
    }

    @Test
    fun `verify when release is called controllable is cleared`() {
        lifeCycleAwareController.release()

        assert(lifeCycleAwareController.controllable == null)
    }

    @Test
    fun `verify no exception thrown when onCreate is called`() {
        lifeCycleAwareController.onCreate()
    }

    @Test
    fun `verify no exception thrown when onStart is called`() {
        lifeCycleAwareController.onStart()
    }

    @Test
    fun `verify no exception thrown when onStop is called`() {
        lifeCycleAwareController.onStop()
    }

    @Test
    fun `verify no exception thrown when onDestroy is called`() {
        lifeCycleAwareController.onDestroy()
    }
}