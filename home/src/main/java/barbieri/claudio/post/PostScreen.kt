package barbieri.claudio.post

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter

@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel(),
    postId : Int,
    navigateToProfile: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.setPostId(postId)
    }

    LaunchedEffect(viewModel) {
        viewModel.uiState.showError.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.event.collect {

        }
    }

    ScreenContent(
        uiState = viewModel.uiState,
        like = viewModel::like,
        follow = viewModel::follow,
        comment = viewModel::comment
    )
}

@Composable
private fun ScreenContent(
    uiState: PostViewModel.UiState,
    like: () -> Unit,
    follow: () -> Unit,
    comment: () -> Unit
) {
    val showProgress = uiState.showProgress.collectAsStateWithLifecycle().value
    val post = uiState.post.collectAsStateWithLifecycle().value
    val comments = uiState.comments.collectAsStateWithLifecycle().value
    val comment = uiState.comment.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    post?.let { presentation ->
                        Spacer(modifier = Modifier.padding(8.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {},
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
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
                                    painter = rememberAsyncImagePainter(presentation.userPhoto),
                                    contentDescription = "Foto",
                                )
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(text = presentation.name)
                                Spacer(modifier = Modifier.padding(8.dp))
                                TextButton(
                                    onClick = { follow() },
                                    shape = RoundedCornerShape(7.dp),
                                    colors = ButtonDefaults.buttonColors(contentColor = if (presentation.isFollowing) Color.Gray else Color.Blue),
                                    content = {
                                        Text(
                                            text = if (presentation.isFollowing) "Seguindo" else "Seguir",
                                            color = Color.White
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.padding(8.dp))
                            if (presentation.image.isNotEmpty()) {
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
                            TextButton(
                                onClick = { like() },
                                shape = RoundedCornerShape(7.dp),
                                colors = ButtonDefaults.buttonColors(contentColor = if (presentation.isFollowing) Color.Gray else Color.Blue),
                                content = {
                                    Text(
                                        text = if (presentation.isLike) "Curtido" else "Curtir",
                                        color = Color.White
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }
                }
                items(comments) { comment ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
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
                            painter = rememberAsyncImagePainter(comment.userPhoto),
                            contentDescription = "Foto",
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = comment.text)
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
            OutlinedTextField(
                value = comment,
                onValueChange = uiState::updateComment,
                label = { Text("Comentário") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                trailingIcon = {
                    IconButton(
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
                        },
                        onClick = { comment() }
                    )
                }
            )
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