package io.tagd.core

import io.tagd.core.fake.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.lang.NullPointerException

@RunWith(MockitoJUnitRunner::class)
class InitializableTest {

    private val initializable: Initializable = FakeInitializable()

    @Test
    fun `given an Initializable then verify initialize is called`() {
        initializable as FakeInitializable
        assert(initializable.initialized)
    }
}