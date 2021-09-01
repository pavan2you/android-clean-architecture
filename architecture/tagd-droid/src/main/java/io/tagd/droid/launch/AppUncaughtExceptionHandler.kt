package io.tagd.droid.launch

import java.lang.ref.WeakReference

open class AppUncaughtExceptionHandler(
    app: TagdApplication,
    defaultHandler: Thread.UncaughtExceptionHandler?
) : Thread.UncaughtExceptionHandler, AppService {

    private var appReference: WeakReference<TagdApplication>? = WeakReference(app)
    private var defaultHandlerReference: WeakReference<Thread.UncaughtExceptionHandler>? =
        WeakReference(defaultHandler)

    override fun uncaughtException(t: Thread, e: Throwable) {
        Thread.getDefaultUncaughtExceptionHandler()?.uncaughtException(t, e)
    }

    override fun release() {
        appReference?.clear()
        appReference = null

        defaultHandlerReference?.clear()
        defaultHandlerReference = null
    }
}