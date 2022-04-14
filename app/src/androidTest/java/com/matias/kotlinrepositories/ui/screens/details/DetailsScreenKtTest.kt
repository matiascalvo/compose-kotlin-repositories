package com.matias.kotlinrepositories.ui.screens.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.theme.KotlinRepositoriesTheme
import com.matias.kotlinrepositories.util.BaseComposeTest
import com.matias.kotlinrepositories.util.DataProvider
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class DetailsScreenKtTest : BaseComposeTest() {

    val repo = DataProvider.provideRepo()

    @Before
    override fun setUp() {
        super.setUp()
        composeTestRule.setContent {
            KotlinRepositoriesTheme {
                DetailsScreen(repo = repo)
            }
        }
    }

    @Test
    fun given_aRepository_When_Displayed_Then_OwnerLogoIsDisplayed() {
        composeTestRule.onNode(hasContentDescription(repo.owner.login)).assertIsDisplayed()
    }

    @Test
    fun given_aRepository_When_Displayed_Then_repositoryNameIsDisplayed() {
        composeTestRule.onNode(hasText(repo.fullName)).assertIsDisplayed()
    }

    @Test
    fun given_aRepository_When_Displayed_Then_homepageIsDisplayed() {
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.home_page))).assertIsDisplayed()
        composeTestRule.onNode(hasText(repo.homepage)).assertIsDisplayed()
    }

    @Test
    fun given_aRepository_When_Displayed_Then_urlIsDisplayed() {
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.repository))).assertIsDisplayed()
        composeTestRule.onNode(hasText(repo.url)).assertIsDisplayed()
    }

    @Test
    fun given_aRepository_When_Displayed_Then_descriptionIsDisplayed() {
        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.description))).assertIsDisplayed()
        composeTestRule.onNode(hasText(repo.description)).assertIsDisplayed()
    }
}
