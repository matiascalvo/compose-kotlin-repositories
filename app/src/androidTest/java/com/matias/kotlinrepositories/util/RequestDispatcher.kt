package com.matias.kotlinrepositories.util

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class RequestDispatcher : Dispatcher() {

    var isEnabled: Boolean = true

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when {
            !isEnabled -> MockResponse().setResponseCode(400)
            request.path == "/search/repositories?q=language%3Akotlin&page=1&s=stars&per_page=30" ->
                MockResponse().setResponseCode(200).setBody(getJsonContent("empty_search_page_1.json"))
            request.path == "/search/repositories?q=language%3Akotlin&page=2&s=stars&per_page=30" ->
                MockResponse().setResponseCode(200).setBody(getJsonContent("empty_search_page_2.json"))
            request.path == "/search/repositories?q=test%2Blanguage%3Akotlin&page=0&s=stars&per_page=30" ->
                MockResponse().setResponseCode(200).setBody(getJsonContent("test_page_1.json"))
            else -> MockResponse().setResponseCode(400)
        }
    }

    private fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}
