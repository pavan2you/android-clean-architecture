package io.tagd.arch.domain.usecase

import io.tagd.core.Releasable
import io.tagd.core.annotation.VisibleForTesting
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicReference

class LiveData<T>(private val args: Args?) : Releasable {

    interface Observer<T> {

        fun onInvalidating()

        fun onChange(value: T, version: Int)

        fun onError(error: Throwable)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val observers = CopyOnWriteArrayList<Observer<T>>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var value: T? = null
        private set

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var valueVersion = NO_DATA_VERSION
        private set

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val invalidate = AtomicReference(false)

    fun setValue(value: T) {
        this.value = value
        valueVersion++

        notifyObservers(value, valueVersion)
    }

    private fun notifyObservers(value: T, version: Int) {
        observers.let {
            do {
                invalidate.set(false)
                notifyObservers(it, value, version)
            } while (invalidate.get() == true)

            if (args?.observe == false) {
                it.clear()
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun notifyObservers(
        list: CopyOnWriteArrayList<Observer<T>>,
        value: T,
        version: Int
    ) {
        val observers = list.toArray(arrayOf<Observer<T>>())
        for (observer in observers) {
            observer.onChange(value, version)
            if (invalidate.get() == true) {
                break
            }
        }
    }

    fun setError(error: Throwable) {
        observers.let {
            for (observer in it) {
                observer.onError(error)
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun addObserver(observer: Observer<T>) {
        observers.add(observer)
        invalidate.set(true)
    }

    fun removeObserver(observer: Observer<T>) {
        observers.remove(observer)
    }

    fun putIfAbsent(observer: Observer<T>): Observer<T>? {
        val pastValue = observers.firstOrNull {
            it == observer
        }
        if (pastValue == null) {
            addObserver(observer)
        }
        return pastValue
    }

    fun removeAll() {
        observers.clear()
    }

    fun dispatchInvalidate() {
        for (observer in observers) {
            observer.onInvalidating()
        }
    }

    override fun release() {
        removeAll()
        invalidate.set(false)
        value = null
        valueVersion = NO_DATA_VERSION
    }

    companion object {
        private const val NO_DATA_VERSION = -1
    }
}