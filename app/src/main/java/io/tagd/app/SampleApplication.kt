package io.tagd.app

import android.os.Handler
import android.os.Looper
import io.tagd.droid.launch.TagdApplication

class SampleApplication : TagdApplication() {

    override fun onLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            dispatchOnLoadingComplete()
        }, 5000)
    }
}