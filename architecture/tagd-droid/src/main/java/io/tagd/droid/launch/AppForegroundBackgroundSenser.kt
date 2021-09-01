package io.tagd.droid.launch

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference

private const val DEFAULT_BG_TIME = 2000L

open class AppForegroundBackgroundSenser(application: TagdApplication) : AppService {

    private var appReference: WeakReference<TagdApplication>? = WeakReference(application)
    protected val app: TagdApplication?
        get() = appReference?.get()

    private var handler: Handler? = Handler(Looper.getMainLooper())
    private var watcher: Runnable? = Runnable {
        senseAppWentToBackground()
    }

    private var backgroundSince: Long = 0

    fun dispatchActivityStart() {
        senseAppCameToForeground()
    }

    fun dispatchActivityStop() {
        backgroundSince = System.currentTimeMillis()
        watchBackground()
    }

    fun dispatchActivityDestroyed(isLast: Boolean = false) {
        senseAppExit(isLast)
    }

    private fun watchBackground() {
        watcher?.let {
            handler?.postDelayed(it, DEFAULT_BG_TIME)
        }
    }

    private fun senseAppCameToForeground() {
        if (backgroundSince == 0L ||
            (backgroundSince > 0L && System.currentTimeMillis() - backgroundSince > DEFAULT_BG_TIME)
        ) {

            removeWatcher()
            backgroundSince = -1L
            app?.onForeground()
        }
    }

    private fun senseAppWentToBackground() {
        if (System.currentTimeMillis() - backgroundSince > DEFAULT_BG_TIME) {
            app?.onBackground()
        }
        removeWatcher()
    }

    private fun senseAppExit(isLast: Boolean) {
        if (isLast) {
            removeWatcher()
            app?.onExit()
        }
    }

    private fun removeWatcher() {
        watcher?.let { handler?.removeCallbacks(it) }
    }

    override fun release() {
        appReference?.clear()
        appReference = null
    }
}
