package io.tagd.javax.lang.ref

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * An extension of WeakReference that implements equals and hashcode
 * method based on the referent.
 *
 * @param <T> The type of object that this reference contains
 */
class ComparableWeakReference<T>(referent: T) : WeakReference<T>(referent) {

    override fun equals(other: Any?): Boolean {
        return if (other is Reference<*>) {
            get() == other.get()
        } else {
            get() == other
        }
    }

    override fun hashCode(): Int {
        return get().hashCode()
    }
}