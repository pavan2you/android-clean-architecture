package io.tagd.arch.presentation.mvb

import io.tagd.arch.data.DataObject
import io.tagd.arch.presentation.View
import io.tagd.arch.data.bind.Bindable

interface BindableView<T : DataObject> : View, Bindable<T> {

    var model: T?

    fun show()

    fun hide()

    fun binder(): Binder<T, out BindableView<T>>?
}