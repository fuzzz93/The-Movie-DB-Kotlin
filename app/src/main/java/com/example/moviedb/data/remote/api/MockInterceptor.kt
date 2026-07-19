package com.example.moviedb.data.remote.api

import android.content.res.AssetManager
import com.example.moviedb.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.HttpURLConnection

class MockInterceptor(
    private val assets: AssetManager
) : Interceptor {
    companion object {
        private const val MOCK_DISCOVER_MOVIE = "mock_discover_movie.json"

        @Volatile
        var forceHttpError: Boolean = false
    }

    /**
     * add mock_response.json to mock
     * comment to use real api
     */
    @Throws(IllegalAccessError::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()

            if (forceHttpError && uri.contains(ApiPath.DISCOVER_MOVIE)) {
                return chain.proceed(chain.request())
                    .newBuilder()
                    .code(HttpURLConnection.HTTP_NOT_FOUND)
                    .protocol(Protocol.HTTP_2)
                    .message("Not Found")
                    .body(
                        "{}".toByteArray()
                            .toResponseBody("application/json".toMediaTypeOrNull())
                    )
                    .addHeader("content-type", "application/json")
                    .build()
            }

            val responseString = when {
                uri.contains("default") -> ""

                // mock api response
                uri.contains(ApiPath.DISCOVER_MOVIE) ->
                    assets.open(MOCK_DISCOVER_MOVIE).bufferedReader().use { it.readText() }
                else -> null
            }

            return if (responseString.isNullOrBlank()) {
                chain.proceed(chain.request())
            } else {
                chain.proceed(chain.request())
                    .newBuilder()
                    .code(HttpURLConnection.HTTP_OK)
                    .protocol(Protocol.HTTP_2)
                    .message(responseString)
                    .body(
                        responseString.toByteArray()
                            .toResponseBody("application/json".toMediaTypeOrNull())
                    )
                    .addHeader("content-type", "application/json")
                    .build()
            }
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }


}
