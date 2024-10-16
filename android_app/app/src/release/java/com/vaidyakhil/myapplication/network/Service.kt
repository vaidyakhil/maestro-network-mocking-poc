package com.vaidyakhil.myapplication.network

import android.content.Context
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class Service(private val context: Context, _testId: String) {

    private val baseUrl = "http://192.168.0.107:3001"

    private val apiService = lazy {
        OkHttpClient.Builder()
            .fastFallback(true)
            .build();
    }

    fun get (endpoint: String, networkCallback: Callback) {
        val request = Request.Builder()
            .url("${baseUrl}${endpoint}")
            .build()

        apiService.value.newCall(request).enqueue(networkCallback)
    }

    fun post(endpoint: String, networkCallback: Callback, body: RequestBody = RequestBody.create
        (null, ByteArray(0))
    ) {
        val request = Request.Builder()
            .post(body)
            .url("${baseUrl}${endpoint}")
            .build()

        apiService.value.newCall(request).enqueue(networkCallback)
    }

}