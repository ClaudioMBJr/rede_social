package barbieri.claudio.login.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiState.showError.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.event.collect {
            when (it) {
                LoginViewModel.ScreenEvent.NavigateToHome -> {
                    /**TODO navega para home **/
                }

                LoginViewModel.ScreenEvent.NavigateToSignup -> {
                    /**TODO navega para registro **/
                }
            }
        }
    }

    ScreenContent(
        uiState = viewModel.uiState,
        login = viewModel::login,
        register = viewModel::sendNavigateToSignupEvent
    )
}

@Composable
private fun ScreenContent(
    uiState: LoginViewModel.UiState,
    login: () -> Unit,
    register: () -> Unit,
) {
    val showProgress = uiState.showProgress.collectAsStateWithLifecycle().value
    val login = uiState.login.collectAsStateWithLifecycle().value
    val password = uiState.password.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (showProgress) CircularProgressIndicator(modifier = Modifier.size(60.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
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
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.weight(1f))
            Row {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { login() },
                    content = {
                        Column(modifier = Modifier.background(color = Color.Blue)) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "Registrar"
                            )
                        }
                    }
                )
                Spacer(Modifier.padding(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { register() },
                    content = {
                        Column(modifier = Modifier.background(color = Color.Blue)) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "Login"
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ScreenContent(
        uiState = LoginViewModel.UiState(),
        login = {},
        register = {}
    )
}