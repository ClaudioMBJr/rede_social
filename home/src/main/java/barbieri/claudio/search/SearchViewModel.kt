package barbieri.claudio.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barbieri.claudio.commons.components.BaseUiState
import barbieri.claudio.commons.domain.model.User
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SocialIfesRepository) :
    ViewModel() {

    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

    val uiState = UiState()

    fun search() {
        viewModelScope.launch {
            uiState.showProgress()
            repository.search(uiState.getQuery()).fold(
                onSuccess = { uiState.setUsers(it) },
                onFailure = { uiState.sendToastMessage(it.message.orEmpty()) }
            )
            uiState.hideProgress()
        }
    }

    fun navigateToProfile(login: String) {
        viewModelScope.launch { _event.send(ScreenEvent.NavigateToProfile(login)) }
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
        data class NavigateToProfile(val login: String) : ScreenEvent()
    }

    class UiState : BaseUiState() {

        val users = MutableStateFlow<List<User>>(emptyList())
        val query = MutableStateFlow(TextFieldValue())

        fun setUsers(users: List<User>) {
            this.users.value = users
        }

        fun setQuery(query: TextFieldValue) {
            this.query.value = query
        }

        fun getQuery() = query.value.text

    }
}