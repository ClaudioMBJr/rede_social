package barbieri.claudio.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barbieri.claudio.commons.components.BaseUiState
import barbieri.claudio.commons.domain.model.UserAuth
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import barbieri.claudio.commons.preferences.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: SocialIfesRepository
) : ViewModel() {

    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

    val uiState = UiState()

    fun login() {
        viewModelScope.launch {
            uiState.showProgress()
            repository.login(uiState.getLogin(), uiState.getPassword()).fold(
                onSuccess = {
                    sendNavigateToHomeEvent()
                },
                onFailure = {
                    uiState.sendToastMessage(it.message.orEmpty())
                }
            )
            uiState.hideProgress()
        }
    }

    private fun sendNavigateToHomeEvent() =
        viewModelScope.launch { _event.send(ScreenEvent.NavigateToHome) }

    fun sendNavigateToSignupEvent() =
        viewModelScope.launch { _event.send(ScreenEvent.NavigateToSignup) }

    sealed class ScreenEvent {
        object NavigateToHome : ScreenEvent()
        object NavigateToSignup : ScreenEvent()
    }

    class UiState : BaseUiState() {
        val login = MutableStateFlow("")
        val password = MutableStateFlow("")

        fun setLogin(login: String) {
            this.login.value = login
        }

        fun setPassword(password: String) {
            this.password.value = password
        }

        fun getLogin() = login.value

        fun getPassword() = password.value
    }
}