package com.example.utexasapadproject3

import android.app.PendingIntent.getActivity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.annotation.MainThread
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

interface HttpUtils {
    @Throws(IOException::class)
    fun doGetRequest(path: String, view: TextView) {
        val baseUrl = MainActivity().baseUrl

        val request = Request.Builder()
            .url(baseUrl + path)
            .build()

        val client = OkHttpClient()
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("doGetRequest Error: ${e}")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val headerList = response.headers()
                    if (headerList["content-type"] == "text/html") {
                        return
                    }

                    val res = response.body()?.string()
                    var json = JSONObject(res)
                    println(json)

                    val handler = Handler(Looper.getMainLooper());
                    handler.post({
                        view.text = res
                    })
                }
            })
    }
}