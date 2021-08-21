package io.tagd.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.tagd.app.R.*

class UsageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.usage_view)

        val injector = Injector()
        injector.setup(this)
        val usage = Usage()
        usage.use()
        usage.release()
    }
}