package barbieri.claudio.search

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import barbieri.claudio.commons.domain.model.User
import coil.compose.rememberAsyncImagePainter

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToProfile: (String) -> Unit
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
                is SearchViewModel.ScreenEvent.NavigateToProfile -> navigateToProfile(it.login)
                SearchViewModel.ScreenEvent.GoBack -> {}
            }
        }
    }

    ScreenContent(
        uiState = viewModel.uiState,
        search = viewModel::search,
        viewModel::navigateToProfile
    )
}

@Composable
private fun ScreenContent(
    uiState: SearchViewModel.UiState,
    search: () -> Unit,
    navigateToProfile: (String) -> Unit
) {
    val showProgress = uiState.showProgress.collectAsStateWithLifecycle().value
    val query = uiState.query.collectAsStateWithLifecycle().value
    val users = uiState.users.collectAsStateWithLifecycle().value
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (showProgress) CircularProgressIndicator(modifier = Modifier.size(60.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { uiState.setQuery(it) },
                label = { Text("Buscar") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions {
                    search()
                    keyboardController?.hide()
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable { search() },
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                    )
                }
            )
            Spacer(Modifier.padding(8.dp))
            users.forEach {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigateToProfile(it.login) }
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
                            painter = rememberAsyncImagePainter(
                                it.image.takeIf { it.isNotEmpty() }
                            ),
                            contentDescription = "Foto",
                        )
                        Spacer(Modifier.padding(8.dp))
                        Text(text = it.name)
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    HorizontalDivider()
                    Spacer(Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ScreenContent(
        uiState = SearchViewModel.UiState(),
        search = {},
        navigateToProfile = {}
    )
}