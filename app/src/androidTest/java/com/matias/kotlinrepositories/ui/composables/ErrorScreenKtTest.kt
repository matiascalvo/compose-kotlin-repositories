package com.matias.kotlinrepositories.ui.composables

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.util.BaseComposeTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class ErrorScreenKtTest : BaseComposeTest() {

    @Test
    fun network_error_displays_correct_message() {
        composeTestRule.setContent {
            NetworkErrorScreen()
        }
        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.no_internet))
            .assertIsDisplayed()
    }

    @Test
    fun unknown_error_displays_correct_message() {
        composeTestRule.setContent {
            UnknownErrorScreen()
        }
        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.unknown_error_try_again))
            .assertIsDisplayed()
    }

    @Test
    fun empty_state_displays_correct_message() {
        composeTestRule.setContent {
            EmptyResultsSearchScreen()
        }
        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.empty_search))
            .assertIsDisplayed()
    }
}
