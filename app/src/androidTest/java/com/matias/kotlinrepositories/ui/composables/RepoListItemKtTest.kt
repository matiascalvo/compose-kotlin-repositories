package com.matias.kotlinrepositories.ui.composables

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import com.matias.domain.model.fakeRepo1
import com.matias.kotlinrepositories.ui.theme.KotlinRepositoriesTheme
import com.matias.kotlinrepositories.util.BaseComposeTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class RepoListItemKtTest : BaseComposeTest() {

    private val repo = fakeRepo1

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
        val stars = composeTestRule.onNode(hasTestTag("stars"), true)
        stars.onChildren().assertAny(hasText(repo.stars.toString()))
    }

    @Test
    fun fork_element_displays_forks_number() {
        val stars = composeTestRule.onNode(hasTestTag("forks"), true)
        stars.onChildren().assertAny(hasText(repo.forks.toString()))
    }

    @Test
    fun full_name_is_displayed() {
        composeTestRule.onNode(hasText(repo.fullName), true).assertIsDisplayed()
    }
}
