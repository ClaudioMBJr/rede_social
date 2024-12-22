package barbieri.claudio.signup

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel)
    {
        viewModel.uiState.showError.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.event.collect {
            when (it) {
                SignupViewModel.ScreenEvent.NavigateToLogin -> {
                    /**TODO navega para login **/
                }
            }
        }
    }

    ScreenContent(uiState = viewModel.uiState, register = viewModel::signup)
}

@Composable
private fun ScreenContent(
    uiState: SignupViewModel.UiState,
    register: () -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uiState.setImage(context.getFileFromUri(uri))
    }

    val showProgress = uiState.showProgress.collectAsStateWithLifecycle().value
    val login = uiState.login.collectAsStateWithLifecycle().value
    val password = uiState.password.collectAsStateWithLifecycle().value
    val city = uiState.password.collectAsStateWithLifecycle().value
    val birthdate = uiState.password.collectAsStateWithLifecycle().value
    val name = uiState.password.collectAsStateWithLifecycle().value
    val image = uiState.password.collectAsStateWithLifecycle().value

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
            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = login,
                onValueChange = { uiState.setLogin(it) },
                label = { Text("Login") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { uiState.setPassword(it) },
                label = { Text("Senha") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { uiState.setName(it) },
                label = { Text("Nome") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = birthdate,
                onValueChange = { uiState.setBirthdate(it) },
                label = { Text("Data de nascimento") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = city,
                onValueChange = { uiState.setCity(it) },
                label = { Text("Cidade") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { register() },
                content = {
                    Column(modifier = Modifier.background(color = Color.Blue)) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Registrar"
                        )
                    }
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
        uiState = SignupViewModel.UiState(),
        register = {}
    )
}