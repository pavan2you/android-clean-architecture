package io.tagd.arch.control.mcc

import io.tagd.arch.control.IApplication
import io.tagd.core.Releasable

interface Controllable : Releasable {

    val app: IApplication?

    fun <C : Controllable> controller(): Controller<C>?
}