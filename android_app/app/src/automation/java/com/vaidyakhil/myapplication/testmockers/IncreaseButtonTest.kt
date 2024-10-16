package com.vaidyakhil.myapplication.testmockers

import android.content.Context
import com.vaidyakhil.myapplication.network.FileReader
import mockwebserver3.MockResponse
import mockwebserver3.RecordedRequest
import org.json.JSONObject

var clickCount = 0;
fun increaseButtonTestMocker (request: RecordedRequest, context: Context): MockResponse {
    return when (request.path) {
        "/getData" ->
            MockResponse().setResponseCode(200)
                .setBody(
                    FileReader.readStringFromFile("networkmocks/getData.json",
                        context))
        "/increase" -> {
            clickCount+=1;
            if (clickCount == 1) {
                return MockResponse().setResponseCode(200)
                    .setBody(
                        FileReader.readStringFromFile("networkmocks/increase.json",
                            context))
            } else if ( clickCount == 2) {
                val originalStringResponse = FileReader.readStringFromFile("networkmocks/increase" +
                        ".json",
                    context)
                val originalJsonResponse = JSONObject(originalStringResponse)
                originalJsonResponse.put("numItems", 2)
                return MockResponse().setResponseCode(200)
                    .setBody(originalJsonResponse.toString())
            } else {
                val response = JSONObject()
                response.put("message", "number of items cannot be more than 2")
                return MockResponse().setResponseCode(500)
                    .setBody(
                        response.toString()
                    )
            }
        }

        else -> MockResponse().setResponseCode(400)
    }
}