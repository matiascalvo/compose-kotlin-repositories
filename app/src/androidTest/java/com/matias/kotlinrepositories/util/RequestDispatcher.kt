package com.matias.kotlinrepositories.util

import java.io.InputStreamReader
import java.net.HttpURLConnection
import javax.inject.Inject
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class RequestDispatcher @Inject constructor() : Dispatcher() {
    var isEnabled: Boolean = true

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when {
            !isEnabled -> MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            request.path == "/search/repositories?q=language%3Akotlin&page=1&s=stars&per_page=30" ->
                MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJsonContent("empty_search_page_1.json"))
            request.path == "/search/repositories?q=language%3Akotlin&page=2&s=stars&per_page=30" ->
                MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJsonContent("empty_search_page_2.json"))
            request.path == "/search/repositories?q=test%2Blanguage%3Akotlin&page=0&s=stars&per_page=30" ->
                MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(getJsonContent("test_page_1.json"))
            else -> MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
        }
    }

    private fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}
