package barbieri.claudio.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barbieri.claudio.commons.components.BaseUiState
import barbieri.claudio.commons.domain.model.Register
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: SocialIfesRepository,
) : ViewModel() {

    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

    val uiState = UiState()

    fun signup() {
        viewModelScope.launch {
            uiState.showProgress()
            repository.register(uiState.getRegister()).fold(
                onSuccess = {
                    uiState.sendToastMessage("Cadastro realizado com sucesso!")
                    sendNavigateToLoginEvent()
                },
                onFailure = {
                    uiState.sendToastMessage(it.message)
                }
            )
            uiState.hideProgress()
        }
    }

    private fun sendNavigateToLoginEvent() =
        viewModelScope.launch { _event.send(ScreenEvent.NavigateToLogin) }

    sealed class ScreenEvent {
        object NavigateToLogin : ScreenEvent()
    }

    class UiState : BaseUiState() {
        val login = MutableStateFlow("")
        val password = MutableStateFlow("")
        val city = MutableStateFlow("")
        val birthdate = MutableStateFlow("")
        val image = MutableStateFlow<File?>(null)
        val name = MutableStateFlow("")

        fun setLogin(login: String) {
            this.login.value = login
        }

        fun setPassword(password: String) {
            this.password.value = password
        }

        fun setCity(city: String) {
            this.city.value = city
        }

        fun setBirthdate(birthdate: String) {
            this.birthdate.value = birthdate
        }

        fun setImage(image: File?) {
            this.image.value = image
        }

        fun setName(name: String) {
            this.name.value = name
        }

        fun getRegister() = Register(
            login = login.value,
            password = password.value,
            city = city.value,
            birthDate = birthdate.value,
            image = image.value,
            name = name.value
        )

    }
}