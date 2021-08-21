package io.tagd.arch.presentation.mvp

import io.tagd.arch.presentation.View

interface PresentableView : View {

    fun <V : PresentableView> presenter(): Presenter<out V>?
}