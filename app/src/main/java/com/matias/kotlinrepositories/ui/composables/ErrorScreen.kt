package com.matias.kotlinrepositories.ui.composables

import android.content.res.Configuration
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.matias.domain.model.NoInternetConnectionException
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.theme.KotlinRepositoriesTheme

@Composable
fun UndefinedErrorScreen(modifier: Modifier = Modifier, e: Throwable, onClickRetry: () -> Unit = {}) {
    when (e) {
        is NoInternetConnectionException -> NetworkErrorScreen(modifier, onClickRetry)
        else -> UnknownErrorScreen(modifier, onClickRetry)
    }
}

@Composable
fun NetworkErrorScreen(modifier: Modifier = Modifier, onClickRetry: () -> Unit = {}) {
    ErrorScreen(
        modifier = modifier,
        animation = R.raw.animation_cat_with_cable,
        res = R.string.no_internet,
        onClickRetry = onClickRetry
    )
}

@Composable
fun UnknownErrorScreen(modifier: Modifier = Modifier, onClickRetry: (() -> Unit)? = {}) {
    ErrorScreen(
        modifier = modifier,
        animation = R.raw.animation_error,
        res = R.string.unknown_error_try_again,
        onClickRetry = onClickRetry
    )
}

@Composable
fun EmptyResultsSearchScreen(modifier: Modifier = Modifier) {
    ErrorScreen(
        modifier = modifier,
        animation = R.raw.animation_empty_state,
        res = R.string.empty_search,
    )
}

@Composable
fun EmptyQuerySearchScreen(modifier: Modifier = Modifier) {
    ErrorScreen(
        modifier = modifier,
        animation = R.raw.animation_magnifier,
        res = R.string.start_searching,
    )
}

@Composable
private fun ErrorScreen(
    modifier: Modifier = Modifier,
    @RawRes animation: Int,
    @StringRes res: Int,
    onClickRetry: (() -> Unit)? = null
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animation))

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(32.dp)
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(200.dp),
            iterations = LottieConstants.IterateForever
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            stringResource(id = res),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        onClickRetry?.let {
            OutlinedButton(onClick = it) {
                Text(text = stringResource(id = R.string.try_again))
            }
        }
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun Preview() {
    KotlinRepositoriesTheme {
        Surface {
            EmptyResultsSearchScreen()
        }
    }
}
