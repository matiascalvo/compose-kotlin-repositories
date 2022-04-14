package com.matias.kotlinrepositories.ui.composables

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onSiblings
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.theme.KotlinRepositoriesTheme
import com.matias.kotlinrepositories.util.BaseComposeTest
import com.matias.kotlinrepositories.util.DataProvider
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class RepoListItemKtTest : BaseComposeTest() {

    private val repo = DataProvider.provideRepo()

    @Before
    override fun setUp() {
        super.setUp()
        composeTestRule.setContent {
            KotlinRepositoriesTheme {
                RepoListItem(repo = repo)
            }
        }
    }

    @Test
    fun stars_element_displays_stars_number() {
        val stars = composeTestRule.onNode(hasContentDescription(getString(R.string.stars)), true)
        stars.onSiblings().assertAny(hasText(repo.stars.toString()))
    }

    @Test
    fun fork_element_displays_forks_number() {
        val stars = composeTestRule.onNode(hasContentDescription(getString(R.string.forks)), true)
        stars.onSiblings().assertAny(hasText(repo.forks.toString()))
    }

    @Test
    fun full_name_is_displayed() {
        composeTestRule.onNode(hasText(repo.fullName), true).assertIsDisplayed()
    }
}
