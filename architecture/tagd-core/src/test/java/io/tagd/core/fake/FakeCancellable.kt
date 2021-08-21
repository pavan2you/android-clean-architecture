package io.tagd.core.fake

import io.tagd.core.Cancellable

class FakeCancellable : Cancellable {

    var cancelled: Boolean = false
    private var cancelledContext: Any? = "context"

    override fun cancel(context: Any?): Boolean {
        if (cancelledContext == context || context == null) {
            cancelled = true
        }
        return cancelled
    }
}