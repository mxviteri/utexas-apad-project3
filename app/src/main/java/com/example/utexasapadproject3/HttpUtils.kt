package com.example.utexasapadproject3

import android.widget.TextView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

interface HttpUtils {
    private val baseUrl: String
        get() = "https://my-project-1530049714771.appspot.com"

    @Throws(IOException::class)
    fun doGetRequest(path: String, callback: (json: JSONObject, code: Int) -> Unit) {
        val baseUrl = baseUrl

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
                    callback(json, response.code())
                }
            })
    }

    @Throws(IOException::class)
    fun doPostRequest(path: String, json: String, callback: (json: JSONObject, code: Int) -> Unit) {
        val baseUrl = baseUrl

        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val request = Request.Builder()
            .url(baseUrl + path)
            .post(body)
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
                    callback(json, response.code())
                }
            })
    }
}