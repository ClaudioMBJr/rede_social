package barbieri.claudio.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToPost: (Int) -> Unit,
    login: String
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.setup(login)
    }

    LaunchedEffect(viewModel) {
        viewModel.uiState.showError.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.event.collect {
            when (it) {
                is ProfileViewModel.ScreenEvent.NavigateToPost -> navigateToPost(it.postId)
            }
        }
    }

    ScreenContent(
        uiState = viewModel.uiState,
        seePost = viewModel::navigateToPost,
        follow = viewModel::follow
    )
}

@Composable
private fun ScreenContent(
    uiState: ProfileViewModel.UiState,
    seePost: (Int) -> Unit,
    follow: () -> Unit
) {
    val showProgress = uiState.showProgress.collectAsStateWithLifecycle().value
    val user = uiState.user.collectAsStateWithLifecycle().value
    val posts = uiState.posts.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        user?.let { user ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Blue,
                                            Color.White
                                        )
                                    )
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .border(
                                        2.dp,
                                        Color.Blue
                                    ),
                                painter = rememberAsyncImagePainter(user.image.takeIf { it.isNotEmpty() }
                                   ),
                                contentDescription = "Foto",
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(text = user.name)
                            Spacer(modifier = Modifier.padding(8.dp))
                            TextButton(
                                onClick = { follow() },
                                shape = RoundedCornerShape(7.dp),
                                colors = ButtonDefaults.buttonColors(contentColor = if (user.following) Color.Gray else Color.Blue),
                                content = {
                                    Text(
                                        text = if (user.following) "Seguindo" else "Seguir",
                                        color = Color.White
                                    )
                                }
                            )
                        }
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

@Composable
private fun Preview() {
    ScreenContent(
        uiState = ProfileViewModel.UiState(),
        seePost = {},
        follow = {}
    )
}