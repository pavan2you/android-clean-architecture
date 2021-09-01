package io.tagd.arch.control

import io.tagd.core.Releasable

interface IApplication : Releasable {

    fun <A : IApplication> controller(): ApplicationController<A>?
}