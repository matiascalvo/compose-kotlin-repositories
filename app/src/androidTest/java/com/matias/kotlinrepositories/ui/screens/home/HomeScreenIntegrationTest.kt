package com.matias.kotlinrepositories.ui.screens.home

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.util.BaseMockWebserverTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class HomeScreenIntegrationTest : BaseMockWebserverTest() {

    @Test
    fun givenNoConnection_Then_NetworkErrorIsDisplayed() {
        mockWebServer.shutdown()

        setHomeScreen()
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.no_internet)), true)
            .assertIsDisplayed()
    }

    @Test
    fun givenUnknownError_Then_UnknownErrorIsDisplayed() {
        disableDispatcher()
        setHomeScreen()

        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.unknown_error_try_again)), true)
            .assertIsDisplayed()
    }

    @Test
    fun whenScreenLoads_Then_CorrectDataIsDisplayed() {
        setHomeScreen()

        val lazyColumn = composeTestRule.onNode(hasScrollAction())

        lazyColumn.onChildAt(0).assert(hasText("square/okhttp")).assertIsDisplayed()
        lazyColumn.onChildAt(1).assert(hasText("JetBrains/kotlin")).assertIsDisplayed()
        lazyColumn.onChildAt(2).assert(hasText("android/architecture-samples")).assertIsDisplayed()
    }

    @Test
    fun when_ScrolledDown_Then_ScrollUpButtonAppears() {
        setHomeScreen()

        composeTestRule.onNode(hasScrollAction()).performScrollToIndex(10)

        composeTestRule.onNode(hasScrollAction()).onChildren()

        composeTestRule.onNode(hasContentDescription(getString(R.string.scroll_to_top))).assertIsDisplayed()
    }

    @Test
    fun when_ClickOnScrollUpButton_Then_FirstItemIsDisplayed() {
        setHomeScreen()

        val scrollable = composeTestRule.onNode(hasScrollAction())

        scrollable.performScrollToIndex(10)

        composeTestRule.onNode(hasContentDescription(getString(R.string.scroll_to_top))).performClick()

        scrollable.onChildAt(0).assert(hasText("square/okhttp"))
    }

    @Test
    fun when_ScrolledToLastElement_Then_NextPageIsLoadedAndFirstElementOfNextPageIsCorrect() {
        setHomeScreen()

        val scrollable = composeTestRule.onNode(hasScrollAction())

        scrollable.performScrollToIndex(29)
        scrollable.performScrollToIndex(30)

        scrollable.onChildAt(0)
            .assert(hasText("YiiGuxing/TranslationPlugin"))
    }

    private fun setHomeScreen() {
        composeTestRule.setContent {
            HomeScreen()
        }
        composeTestRule.waitForIdle()
    }
}
