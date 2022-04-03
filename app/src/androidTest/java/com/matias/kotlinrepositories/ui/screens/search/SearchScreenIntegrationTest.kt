package com.matias.kotlinrepositories.ui.screens.search

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.composables.REPO_LIST
import com.matias.kotlinrepositories.util.BaseMockWebserverTest
import dagger.hilt.android.testing.HiltAndroidTest
import java.net.HttpURLConnection
import org.junit.Test

@HiltAndroidTest
class SearchScreenIntegrationTest : BaseMockWebserverTest() {

    @Test
    fun givenNoSearchTerm_Then_EmptyStateIsShown() {
        setScreen()

        composeTestRule
            .onNode(hasText(composeTestRule.activity.getString(R.string.start_searching)), true)
            .assertIsDisplayed()
    }

    @Test
    fun givenNoSearchTerm_WhenInputChangesToTest_Then_CorrectDataIsLoadedFromBackend() {
        enqueueFile("test_page_1.json", HttpURLConnection.HTTP_OK)
        setScreen()

        composeTestRule.onRoot().printToLog("pampa")

        composeTestRule.onNode(hasText(getString(R.string.search))).performTextInput("test")
        val lazyColumn = composeTestRule.onNode(hasTestTag(REPO_LIST))

        lazyColumn.onChildAt(0).assert(hasText("kotest/kotest")).assertIsDisplayed()
        lazyColumn.onChildAt(1).assert(hasText("googlecodelabs/android-testing")).assertIsDisplayed()
        lazyColumn.onChildAt(2).assert(hasText("KasperskyLab/Kaspresso")).assertIsDisplayed()
    }

    private fun setScreen() {
        composeTestRule.setContent {
            SearchScreen()
        }
    }
}
