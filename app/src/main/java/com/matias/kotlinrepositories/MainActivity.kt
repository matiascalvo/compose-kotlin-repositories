package com.matias.kotlinrepositories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.matias.kotlinrepositories.ui.screens.home.HomeScreen
import com.matias.kotlinrepositories.ui.theme.KotlinRepositoriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinRepositoriesTheme {
                HomeScreen()
            }
        }
    }
}
