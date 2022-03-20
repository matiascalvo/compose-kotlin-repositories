package com.matias.kotlinrepositories.ui.screens.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.matias.domain.model.fakeRepo1
import com.matias.kotlinrepositories.MainActivity
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.theme.KotlinRepositoriesTheme
import com.matias.kotlinrepositories.util.BaseMockWebserverTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DetailsScreenKtTest : BaseMockWebserverTest() {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    override fun setUp() {
        super.setUp()
        composeTestRule.setContent {
            KotlinRepositoriesTheme {
                DetailsScreen(repo = fakeRepo1)
            }
        }
    }

    @Test
    fun given_aRepository_When_Displayed_Then_OwnerLogoIsDisplayed() {
        composeTestRule.onNode(hasContentDescription(fakeRepo1.owner.login)).assertIsDisplayed()
    }

    @Test
    fun given_aRepository_When_Displayed_Then_repositoryNameIsDisplayed() {
        composeTestRule.onNode(hasText(fakeRepo1.fullName)).assertIsDisplayed()
    }

    @Test
    fun given_aRepository_When_Displayed_Then_homepageIsDisplayed() {
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.home_page))).assertIsDisplayed()
        composeTestRule.onNode(hasText(fakeRepo1.homepage)).assertIsDisplayed()
    }

    @Test
    fun given_aRepository_When_Displayed_Then_urlIsDisplayed() {
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.repository))).assertIsDisplayed()
        composeTestRule.onNode(hasText(fakeRepo1.url)).assertIsDisplayed()
    }

    @Test
    fun given_aRepository_When_Displayed_Then_descriptionIsDisplayed() {
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.description))).assertIsDisplayed()
        composeTestRule.onNode(hasText(fakeRepo1.description)).assertIsDisplayed()
    }
}
