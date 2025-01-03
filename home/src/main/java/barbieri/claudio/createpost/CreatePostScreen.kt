package barbieri.claudio.createpost

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import barbieri.claudio.commons.ext.getFileFromUri
import coil.compose.rememberAsyncImagePainter

@Composable
fun CreatePostScreen(
    viewModel: CreatePostViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiState.showError.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                CreatePostViewModel.ScreenEvent.GoBack -> goBack()
            }
        }
    }

    ScreenContent(uiState = viewModel.uiState, post = viewModel::post)
}

@Composable
private fun ScreenContent(
    uiState: CreatePostViewModel.UiState,
    post: () -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uiState.setImage(context.getFileFromUri(uri))
    }

    val showProgress = uiState.showProgress.collectAsStateWithLifecycle().value
    val post = uiState.post.collectAsStateWithLifecycle().value
    val image = uiState.image.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (showProgress) CircularProgressIndicator(modifier = Modifier.size(60.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (image != null) {
                Image(
                    painter = rememberAsyncImagePainter(image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            } else {
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(20.dp),
                    onClick = { launcher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.Blue),
                    content = {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Adicionar imagem",
                            color = Color.White
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = post,
                onValueChange = { uiState.setPost(it) },
                label = { Text("Post") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { post() },
                colors = ButtonDefaults.buttonColors(contentColor = Color.Blue),
                content = {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Post",
                        color = Color.White
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ScreenContent(
        uiState = CreatePostViewModel.UiState(),
        post = {}
    )
}