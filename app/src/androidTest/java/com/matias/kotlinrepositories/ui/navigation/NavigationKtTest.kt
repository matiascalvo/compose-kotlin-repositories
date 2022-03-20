package com.matias.kotlinrepositories.ui.navigation

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.espresso.Espresso
import com.matias.kotlinrepositories.MainActivity
import com.matias.kotlinrepositories.ui.screens.home.HOME_LIST_TEST_TAG
import com.matias.kotlinrepositories.util.BaseMockWebserverTest
import dagger.hilt.android.testing.HiltAndroidTest
import java.net.HttpURLConnection
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationKtTest : BaseMockWebserverTest() {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController

    @Test
    fun given_NoAction_Then_CurrentDestinationIsHome() {
        enqueueFile("empty_page_1.json", HttpURLConnection.HTTP_OK)
        setMainNavigation()

        assertEquals(Screen.Home.route, navController.currentDestination?.route)
    }

    @Test
    fun given_emptySearch_When_ClickedOnFirstElement_Then_CurrentDestinationIsDetails() {
        enqueueFile("empty_page_1.json", HttpURLConnection.HTTP_OK)
        setMainNavigation()

        composeTestRule.onNode(hasTestTag(HOME_LIST_TEST_TAG)).onChildAt(0).performClick()

        assertEquals(Screen.Details.route, navController.currentDestination?.route)
        assertEquals("square", navController.backQueue.last().arguments?.get("owner").toString())
        assertEquals("okhttp", navController.backQueue.last().arguments?.get("name").toString())
    }

    @Test
    fun given_emptySearchAndClickedOnFirstItem_When_ClickedOnBack_Then_CurrentDestinationIsHome() {
        enqueueFile("empty_page_1.json", HttpURLConnection.HTTP_OK)
        setMainNavigation()

        composeTestRule.onNode(hasTestTag(HOME_LIST_TEST_TAG)).onChildAt(0).performClick()
        Espresso.pressBack()

        assertEquals(Screen.Home.route, navController.currentDestination?.route)
    }

    private fun setMainNavigation() {
        composeTestRule.setContent {
            navController = rememberNavController()
            Navigation(navController)
        }
    }
}
