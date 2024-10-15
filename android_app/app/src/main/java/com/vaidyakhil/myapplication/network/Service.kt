package com.vaidyakhil.myapplication.network

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

object Service {

    private const val BASE_URL = "http://192.168.0.107:3001"

    private val apiService = lazy {
        OkHttpClient.Builder()
            .fastFallback(true)
            .build();
    }
    
    fun get (endpoint: String, networkCallback: Callback) {
        val request = Request.Builder()
            .url("${BASE_URL}${endpoint}")
            .build()

        apiService.value.newCall(request).enqueue(networkCallback)
    }

    fun post(endpoint: String, networkCallback: Callback, body: RequestBody = RequestBody.create
        (null, ByteArray(0))
                     ) {
        val request = Request.Builder()
            .post(body)
            .url("${BASE_URL}${endpoint}")
            .build()

        apiService.value.newCall(request).enqueue(networkCallback)
    }

}