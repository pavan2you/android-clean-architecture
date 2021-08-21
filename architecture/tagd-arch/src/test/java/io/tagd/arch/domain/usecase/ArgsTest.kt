package io.tagd.arch.domain.usecase

import com.nhaarman.mockito_kotlin.spy
import io.tagd.core.stateOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArgsTest {

    private val state: Args = spy()

    @Test //todo: Duplicate test for the sake of test coverage
    fun `given data is cleared then verify state is empty`() {
        state.put("float", 1.0f)
        assert(!state.isEmpty())

        state.clear()
        assert(state.isEmpty())
    }

    @Test //todo: Duplicate test for the sake of test coverage
    fun `given state is released then verify state is empty`() {
        state.put("float", 1.0f)
        assert(!state.isEmpty())

        state.release()
        assert(state.isEmpty())
    }

    @Test //todo: Duplicate test for the sake of test coverage
    fun `given state of is called then verify state object is created`() {
        val state = stateOf("one" to 1)
        assert(state.get<Int>("one") == 1)
    }

    @Test
    fun `given argsOf is called then verify state object is created`() {
        val state = argsOf("one" to 1)
        assert(state.get<Int>("one") == 1)
    }
}