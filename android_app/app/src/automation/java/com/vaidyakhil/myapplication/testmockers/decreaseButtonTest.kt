package com.vaidyakhil.myapplication.testmockers

import android.content.Context
import com.vaidyakhil.myapplication.network.FileReader
import mockwebserver3.MockResponse
import mockwebserver3.RecordedRequest
import org.json.JSONObject

var decreaseClickCount = 0;
fun decreaseButtonTestMocker (request: RecordedRequest, context: Context): MockResponse {
    return when (request.path) {
        "/getData" ->
            MockResponse().setResponseCode(200)
                .setBody(
                    FileReader.readStringFromFile("networkmocks/getData.json",
                        context))
        "/increase" -> {
            return MockResponse().setResponseCode(200)
                .setBody(
                    FileReader.readStringFromFile("networkmocks/increase.json",
                        context))
        }
        "/decrease" -> {
            decreaseClickCount+=1;
            if (decreaseClickCount == 1) {
                val response = JSONObject()
                response.put("numItems", 0)
                return MockResponse().setResponseCode(200)
                    .setBody(response.toString())
            } else {
                return MockResponse().setResponseCode(500)
                    .setBody(
                        FileReader.readStringFromFile("networkmocks/decrease.json",
                            context))
            }
        }

        else -> MockResponse().setResponseCode(400)
    }
}