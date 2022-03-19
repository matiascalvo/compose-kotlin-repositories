package com.matias.kotlinrepositories.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.matias.domain.model.Repo
import com.matias.domain.model.fakeRepo1
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.theme.KotlinRepositoriesTheme

@Composable
fun RepoListItem(repo: Repo, onClick: (Repo) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .clickable(onClick = { onClick.invoke(repo) })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(repo.owner.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = repo.owner.login,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(64.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.size(16.dp))
            RepoContent(repo)
        }
    }
}

@Composable
private fun RepoContent(repo: Repo) {
    Column {
        Text(text = repo.fullName, maxLines = 1, overflow = TextOverflow.Ellipsis)
        SmallIconWithAmount(
            modifier = Modifier.testTag("stars"),
            text = repo.stars.toString(),
            painter = rememberVectorPainter(image = Icons.Default.Star),
            contentDescription = stringResource(id = R.string.stars)
        )
        SmallIconWithAmount(
            modifier = Modifier.testTag("forks"),
            text = repo.forks.toString(),
            painter = painterResource(id = R.drawable.ic_fork),
            contentDescription = stringResource(id = R.string.forks)
        )
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
        RepoListItem(repo = fakeRepo1)
    }
}
