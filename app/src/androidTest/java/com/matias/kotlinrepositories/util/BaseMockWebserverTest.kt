package com.matias.kotlinrepositories.util

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import java.io.InputStreamReader
import java.net.HttpURLConnection
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseMockWebserverTest : BaseHiltTest() {

    @Inject
    protected lateinit var okHttp: OkHttpClient
    protected val mockWebServer = MockWebServer()
    private lateinit var idlingResource: OkHttp3IdlingResource

    @Before
    override fun setUp() {
        super.setUp()
        idlingResource = OkHttp3IdlingResource.create("okhttp", okHttp)
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    protected fun enqueueContent(content: String, code: Int = HttpURLConnection.HTTP_OK) {
        mockWebServer.enqueue(MockResponse().setResponseCode(code).setBody(content))
    }

    protected fun enqueueFile(fileName: String, code: Int = HttpURLConnection.HTTP_OK) {
        val fileContent = getJsonContent(fileName)
        enqueueContent(fileContent, code)
    }

    private fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}
