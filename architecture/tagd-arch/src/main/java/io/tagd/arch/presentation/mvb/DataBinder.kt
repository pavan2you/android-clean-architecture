package io.tagd.arch.presentation.mvb

import io.tagd.arch.data.DataObject
import java.lang.ref.WeakReference

abstract class DataBinder<T: DataObject, V: BindableView<T>>(view: V) : Binder<T, V> {

    private val viewReference = WeakReference(view)

    override val view: V?
        get() = viewReference.get()

    override fun onCreate() {
        //Optional
    }

    override fun onUnbind() {
        //Optional
    }

    override fun onDestroy() {
        release()
    }

    override fun release() {
        viewReference.clear()
    }
}