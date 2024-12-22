package barbieri.claudio.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import barbieri.claudio.commons.domain.model.Post
import coil.compose.rememberAsyncImagePainter

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiState.showError.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.event.collect {

        }
    }

    ScreenContent(
        uiState = viewModel.uiState,
        updateList = { viewModel.getPosts(true) }
    )
}

@Composable
private fun ScreenContent(
    uiState: HomeViewModel.UiState,
    updateList: () -> Unit,
) {
    val showProgress = uiState.showProgress.collectAsStateWithLifecycle().value
    val posts = uiState.posts.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.padding(8.dp))
                }
                items(posts) { post ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .border(
                                        2.dp,
                                        Color.Blue
                                    ),
                                painter = rememberAsyncImagePainter(post.userPhoto),
                                contentDescription = "Foto",
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(text = post.name)
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        if (post.image.isNotEmpty()) {
                            Image(
                                painter = rememberAsyncImagePainter(post.image),
                                modifier = Modifier
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(7.dp)),
                                contentDescription = "Foto",
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                        Text(text = post.text)
                        Spacer(modifier = Modifier.padding(8.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
        if (showProgress) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(60.dp))
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ScreenContent(
        uiState = HomeViewModel.UiState().apply {
            setPosts(listOf(Post.mock(), Post.mock(), Post.mock(), Post.mock(), Post.mock()))
        },
        updateList = {}
    )
}
