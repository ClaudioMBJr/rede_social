package barbieri.claudio.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToPost: (Int) -> Unit,
    navigateToCreatePost: () -> Unit,
    navigateToSearch: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.getPosts()
    }

    LaunchedEffect(viewModel) {
        viewModel.uiState.showError.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.event.collect {
            when (it) {
                is HomeViewModel.ScreenEvent.NavigateToPost -> navigateToPost(it.postId)
                is HomeViewModel.ScreenEvent.NavigateToCreatePost -> navigateToCreatePost()
                is HomeViewModel.ScreenEvent.NavigateToSearch -> navigateToSearch()
            }
        }
    }

    ScreenContent(
        uiState = viewModel.uiState,
        seePost = viewModel::navigateToPost,
        createPost = viewModel::navigateToCreatePost,
        search = viewModel::navigateToSearch
    )
}

@Composable
private fun ScreenContent(
    uiState: HomeViewModel.UiState,
    seePost: (Int) -> Unit,
    createPost: () -> Unit,
    search: () -> Unit,
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                seePost(post.id)
                            },
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
                                painter = rememberAsyncImagePainter(post.userPhoto.takeIf { it.isNotEmpty() }
                                   ),
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
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .size(35.dp),
                onClick = { search() },
            ) {
                Icon(Icons.Filled.Search, "Buscar")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            FloatingActionButton(
                modifier = Modifier
                    .size(40.dp),
                onClick = { createPost() },
            ) {
                Icon(Icons.Filled.Create, "Criar post")
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
        search = {},
        seePost = {},
        createPost = {}
    )
}
