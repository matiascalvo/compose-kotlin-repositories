package com.matias.kotlinrepositories.ui.screens.home

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import com.matias.kotlinrepositories.MainActivity
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.composables.TOP_SEARCH_FIELD_TEST_TAG
import com.matias.kotlinrepositories.util.BaseMockWebserverTest
import dagger.hilt.android.testing.HiltAndroidTest
import java.net.HttpURLConnection
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenIntegrationTest : BaseMockWebserverTest() {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun givenNoConnection_Then_NetworkErrorIsDisplayed() {
        mockWebServer.shutdown()

        composeTestRule.setContent {
            HomeScreen()
        }
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.no_internet)), true)
            .assertIsDisplayed()
    }

    @Test
    fun givenUnknownError_Then_UnknownErrorIsDisplayed() {
        enqueueContent("", HttpURLConnection.HTTP_BAD_REQUEST)
        composeTestRule.setContent {
            HomeScreen()
        }
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.unknown_error)), true)
            .assertIsDisplayed()
    }

    @Test
    fun givenNoSearchTerm_Then_CorrectDataIsDisplayed() {
        enqueueFile("empty_page_1.json", HttpURLConnection.HTTP_OK)
        composeTestRule.setContent {
            HomeScreen()
        }

        val lazyColumn = composeTestRule.onNode(hasTestTag(HOME_LIST_TEST_TAG))

        lazyColumn.onChildAt(0).assert(hasText("square/okhttp")).assertIsDisplayed()
        lazyColumn.onChildAt(1).assert(hasText("JetBrains/kotlin")).assertIsDisplayed()
        lazyColumn.onChildAt(2).assert(hasText("android/architecture-samples")).assertIsDisplayed()
    }

    @Test
    fun givenNoSearchTerm_When_ScrolledDown_Then_ScrollUpButtonAppears() {
        enqueueFile("empty_page_1.json", HttpURLConnection.HTTP_OK)
        composeTestRule.setContent {
            HomeScreen()
        }

        composeTestRule.onNode(hasTestTag(HOME_LIST_TEST_TAG)).performScrollToIndex(10)

        composeTestRule.onNode(hasTestTag(SCROLL_TO_TOP_TEST_TAG)).assertIsDisplayed()
    }

    @Test
    fun givenNoSearchTermAndScrolledDown_When_ClickOnScrollUpButton_Then_FirstItemIsDisplayed() {
        enqueueFile("empty_page_1.json", HttpURLConnection.HTTP_OK)
        composeTestRule.setContent {
            HomeScreen()
        }

        composeTestRule.onNode(hasTestTag(HOME_LIST_TEST_TAG)).performScrollToIndex(10)
        composeTestRule.onNode(hasTestTag(SCROLL_TO_TOP_TEST_TAG)).performClick()

        composeTestRule.onNode(hasTestTag(HOME_LIST_TEST_TAG)).onChildAt(0).assert(hasText("square/okhttp"))
    }

    @Test
    fun givenNoSearchTerm_When_ScrolledToLastElement_Then_NextPageIsLoadedAndFirstElementOfNextPageIsCorrect() {

        enqueueFile("empty_page_1.json", HttpURLConnection.HTTP_OK)
        enqueueFile("empty_page_2.json", HttpURLConnection.HTTP_OK)
        composeTestRule.setContent {
            HomeScreen()
        }

        composeTestRule.onNode(hasTestTag(HOME_LIST_TEST_TAG)).performScrollToIndex(29)
        composeTestRule.onNode(hasTestTag(HOME_LIST_TEST_TAG)).performScrollToIndex(30)

        composeTestRule.onNode(hasTestTag(HOME_LIST_TEST_TAG)).onChildAt(0)
            .assert(hasText("YiiGuxing/TranslationPlugin"))
    }

    @Test
    fun givenNoSearchTerm_WhenInputChangesToTest_Then_CorrectDataIsLoadedFromBackend() {
        enqueueFile("empty_page_1.json", HttpURLConnection.HTTP_OK)
        enqueueFile("test_page_1.json", HttpURLConnection.HTTP_OK)
        composeTestRule.setContent {
            HomeScreen()
        }

        composeTestRule.onNode(hasTestTag(TOP_SEARCH_FIELD_TEST_TAG)).performTextInput("test")
        val lazyColumn = composeTestRule.onNode(hasTestTag(HOME_LIST_TEST_TAG))

        lazyColumn.onChildAt(0).assert(hasText("kotest/kotest")).assertIsDisplayed()
        lazyColumn.onChildAt(1).assert(hasText("googlecodelabs/android-testing")).assertIsDisplayed()
        lazyColumn.onChildAt(2).assert(hasText("KasperskyLab/Kaspresso")).assertIsDisplayed()
    }
}
