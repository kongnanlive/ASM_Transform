package github.leavesczy.asm.okHttp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import github.leavesczy.asm.R
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)
        findViewById<View>(R.id.button).setOnClickListener {
            okHttpDemo()
        }
    }

    private fun okHttpDemo() {
        Thread {
            val response: String? = run("https://raw.github.com/square/okhttp/master/README.md")
            println(response)
        }.start()
    }

    private fun run(url: String): String? {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
                .url(url)
                .build()
        client.newCall(request).execute().use { response -> return response.body!!.string() }
    }

}