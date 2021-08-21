package io.tagd.core

/**
 * Typically a long running work classes would be implementing the [Cancellable] behaviour.
 * This would be one of the side effect the long running work classes must handle, to gracefully
 * handle ongoing work cancellation.
 */
interface Cancellable {

    /**
     * Call this, to cancel the cancellable work.
     * @param context, here serves as the origin/source from which the call is made.
     * @return true, if cancellation is successful otherwise false.
     */
    fun cancel(context: Any? = null): Boolean
}