package io.tagd.arch.present

import io.tagd.arch.control.IApplication
import io.tagd.core.Releasable

interface View : Releasable {

    val app: IApplication?
}