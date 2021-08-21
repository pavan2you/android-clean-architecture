package io.tagd.arch.data.bind

import io.tagd.core.Releasable

/**
 * A bindable binds itself to the given Type T
 */
interface Bindable<T : Any> : Releasable {

    /**
     * Typically the binding managers or binders or state changeable
     */
    fun bindTo(subject: T)
}