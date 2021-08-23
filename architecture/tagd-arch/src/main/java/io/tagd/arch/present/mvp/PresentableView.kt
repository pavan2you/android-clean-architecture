package io.tagd.arch.present.mvp

import io.tagd.arch.present.View

interface PresentableView : View {

    fun <V : PresentableView> presenter(): Presenter<out V>?
}