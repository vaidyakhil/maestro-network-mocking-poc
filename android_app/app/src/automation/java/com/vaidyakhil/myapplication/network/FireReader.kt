package com.vaidyakhil.myapplication.network

import android.content.Context
import android.util.Log
import java.io.IOException
import java.io.InputStreamReader

object FileReader {
    fun readStringFromFile(fileName: String, context: Context): String {
        try {
            val inputStream = context.assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            Log.d("network-mock", builder.toString())
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}