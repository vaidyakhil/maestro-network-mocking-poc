package com.vaidyakhil.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.vaidyakhil.myapplication.databinding.ActivityMainBinding
import com.vaidyakhil.myapplication.network.Service
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var service: Service

    private val networkCallback = object : Callback {
        override fun onResponse(call: Call, response: Response) {
            val stringRes = response.body.string()
            Log.d("network-mocking", "${response.request.url}: $stringRes")
            val jsonRes = JSONObject(stringRes)
            if (response.code == 200) {
                runOnUiThread {
                    val newVal = jsonRes.getInt("numItems")
                    binding.numItems.visibility = View.VISIBLE
                    binding.numItems.text = newVal.toString()

                    if (newVal != 1) {
                        binding.numItems.setTextColor(resources.getColor(R.color.danger,
                            this@MainActivity.theme))
                    } else {
                        binding.numItems.setTextColor(resources.getColor(R.color.primary,
                            this@MainActivity.theme))
                    }
                }
            } else {
                // contract: user facing error-message in message field
                Snackbar.make(binding.root, jsonRes.getString("message"), Snackbar
                    .LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            val stringRes = e.message
            Log.d("network-mocking", "$stringRes")

            Snackbar.make(binding.root, "something went wrong!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun decreaseItem () {
        service.post("/decrease", networkCallback)
    }

    private fun increaseItem () {
        service.post("/increase", networkCallback)
    }

    private fun fetchData() {
        service.get("/getData", networkCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        service = Service(this@MainActivity, intent.getStringExtra("test-id") ?: "")

        fetchData()
        binding.buttonDecrease.setOnClickListener {
            decreaseItem()
        }

        binding.buttonIncrease.setOnClickListener {
            increaseItem()
        }
    }
}