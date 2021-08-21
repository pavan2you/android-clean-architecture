package io.tagd.arch.data.bind

import io.tagd.core.Initializable
import io.tagd.core.annotation.VisibleForTesting
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArraySet

open class BindableSubject : Initializable {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var bindables = CopyOnWriteArraySet<WeakReference<Bindable<out BindableSubject>>>()

    override fun initialize() {
        bindables = CopyOnWriteArraySet<WeakReference<Bindable<out BindableSubject>>>()
    }

    fun add(bindable: Bindable<out BindableSubject>) {
        remove(bindable)
        bindables.add(WeakReference(bindable))
    }

    fun remove(bindable: Bindable<out BindableSubject>) {
        bindables.firstOrNull {
            it.get() == bindable
        }?.let {
            bindables.remove(it)
        }
    }

    fun removeAllBindables() {
        bindables.clear()
    }

    @Suppress("UNCHECKED_CAST")
    fun addBindablesFrom(source: BindableSubject) {
        source.bindables.forEach {
            it.get()?.let { bindable ->
                add(bindable as Bindable<BindableSubject>)
            }
        }
    }

    fun switchBindingsTo(other: BindableSubject) {
        other.addBindablesFrom(this)
    }

    @Suppress("UNCHECKED_CAST")
    fun notifyBindables() {
        bindables.forEach {
            (it.get() as? Bindable<BindableSubject>)?.bindTo(this)
        }
    }
}

