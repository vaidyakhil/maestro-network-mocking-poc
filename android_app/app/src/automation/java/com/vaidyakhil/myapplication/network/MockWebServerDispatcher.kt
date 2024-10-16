package com.vaidyakhil.myapplication.network

import android.content.Context
import com.vaidyakhil.myapplication.testmockers.decreaseButtonTestMocker
import com.vaidyakhil.myapplication.testmockers.increaseButtonTestMocker
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.RecordedRequest

class MockWebServerDispatcher(private val context: Context, private val testId: String) {
    internal inner class RequestDispatcher : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
            return when (testId) {
                "increase-button" ->
                    increaseButtonTestMocker(request, context)
                "decrease-button" ->
                    decreaseButtonTestMocker(request, context)
                else -> MockResponse().setResponseCode(400).setBody("invalid test-id passed")
            }
        }
    }

    internal inner class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(400)
                .setBody("ErrorDispatcher: something went wrong")
        }
    }
}