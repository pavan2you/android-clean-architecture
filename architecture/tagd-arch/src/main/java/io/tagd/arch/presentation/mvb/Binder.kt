package io.tagd.arch.presentation.mvb

import io.tagd.arch.data.DataObject
import io.tagd.core.Releasable

/**
 * Represents Binder in Model-View-Binder(ViewModel).
 *
 * The primary responsibility is to glue model to the view. Optionally / in complete sense, it will
 * listen to UI changes and emits / delegates to model emitters to produce a new model.
 *
 * To handle ui changes, the recommendation is to implement onChangeXXX(xxxValue) { ... }
 * Or having a template method onChange(elementId, elementValue) { ... }
 */
interface Binder<T : DataObject, V : BindableView<T>> : Releasable {

    val view: V?

    fun onCreate()

    fun onBind(model: T, vararg optionals: Any?)

    fun onUnbind()

    fun onDestroy()
}