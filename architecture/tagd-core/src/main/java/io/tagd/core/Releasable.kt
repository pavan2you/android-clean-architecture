package io.tagd.core

/**
 * Any class which obeys [Releasable] contract, must handle the resource freeing logic in
 * [Releasable.release].
 *
 * This would serve as a good engineering practice to gracefully let the objects eligible for
 * garbage collection.
 */
interface Releasable {

    /**
     * Call this to release a [Releasable] object.
     */
    fun release()
}