package com.xygo.app

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

suspend fun getRouteDistance(
    context: Context,
    pickup: String,
    drop: String
): Double? {

    Log.d(
        "XYGO_ROUTE",
        "getRouteDistance CALLED: pickup=$pickup, drop=$drop"
    )

    return withContext(Dispatchers.IO) {

        try {

            val applicationInfo = context.packageManager
                .getApplicationInfo(
                    context.packageName,
                    android.content.pm.PackageManager.GET_META_DATA
                )

            val apiKey = applicationInfo.metaData
                ?.getString("com.google.android.geo.API_KEY")

            Log.d(
                "XYGO_ROUTE",
                "API key found: ${!apiKey.isNullOrBlank()}"
            )

            if (apiKey.isNullOrBlank()) {
                Log.e(
                    "XYGO_ROUTE",
                    "API key not found in AndroidManifest"
                )
                return@withContext null
            }

            val url = URL(
                "https://routes.googleapis.com/directions/v2:computeRoutes"
            )

            val connection =
                url.openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.doOutput = true

            connection.setRequestProperty(
                "Content-Type",
                "application/json"
            )

            connection.setRequestProperty(
                "X-Goog-Api-Key",
                apiKey
            )

            connection.setRequestProperty(
                "X-Goog-FieldMask",
                "routes.distanceMeters,routes.duration"
            )

            val requestBody = JSONObject().apply {

                put(
                    "origin",
                    JSONObject().apply {
                        put("address", pickup)
                    }
                )

                put(
                    "destination",
                    JSONObject().apply {
                        put("address", drop)
                    }
                )

                put(
                    "travelMode",
                    "DRIVE"
                )
            }

            connection.outputStream.use { output ->
                output.write(
                    requestBody
                        .toString()
                        .toByteArray()
                )
            }

            val responseCode = connection.responseCode

            if (responseCode !in 200..299) {

                val errorMessage =
                    connection.errorStream
                        ?.bufferedReader()
                        ?.use { it.readText() }

                Log.e(
                    "XYGO_ROUTE",
                    "Routes API Error $responseCode: $errorMessage"
                )

                return@withContext null
            }

            val response =
                connection.inputStream
                    .bufferedReader()
                    .use { it.readText() }

            Log.d(
                "XYGO_ROUTE",
                "Routes API Response: $response"
            )

            val json =
                JSONObject(response)

            val routes =
                json.optJSONArray("routes")
                    ?: return@withContext null

            if (routes.length() == 0) {
                return@withContext null
            }

            val distanceMeters =
                routes
                    .getJSONObject(0)
                    .optDouble(
                        "distanceMeters",
                        0.0
                    )

            distanceMeters / 1000.0

        } catch (e: Exception) {

            Log.e(
                "XYGO_ROUTE",
                "Routes API Exception",
                e
            )

            null
        }
    }
}