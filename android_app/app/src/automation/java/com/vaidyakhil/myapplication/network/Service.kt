package com.vaidyakhil.myapplication.network

import android.content.Context
import mockwebserver3.MockWebServer
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import kotlin.concurrent.thread

class Service(private val context: Context, testId: String?) {


    private val baseUrl = "http://127.0.0.1:8080"
    private val mockWebServer = MockWebServer()

    private val apiService = lazy {
        OkHttpClient.Builder()
            .fastFallback(true)
            .build();
    }

    init {
        if (testId.isNullOrEmpty()) {
            throw Exception("InvalidLaunchOptions: please pass test-id while launching the app")
        }

        // will start a server on the loopback interface
        thread {
            mockWebServer.start(8080)
            mockWebServer.dispatcher = MockWebServerDispatcher(context, testId).RequestDispatcher()
        }
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