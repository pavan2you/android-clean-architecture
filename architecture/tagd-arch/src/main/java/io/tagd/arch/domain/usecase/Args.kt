package io.tagd.arch.domain.usecase

import io.tagd.arch.domain.usecase.Args.Companion.CONTEXT
import io.tagd.arch.domain.usecase.Args.Companion.OBSERVE
import io.tagd.core.State

open class Args(
    val observe: Boolean = true,
    val context: Any? = null
) : State() {

    companion object {
        const val OBSERVE = "observe"
        const val CONTEXT = "context"
    }
}

fun argsOf(vararg pairs: Pair<String, Any?>): Args {
    return Args(
        observe = pairs.firstOrNull { observePair -> observePair.first == OBSERVE }
            ?.let { observePair -> observePair.second as Boolean }
            ?: true,
        context = pairs.firstOrNull { contextPair -> contextPair.first == CONTEXT }?.second
    ).apply {
        putAll(pairs)
    }
}