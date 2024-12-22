package barbieri.claudio.commons.components

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow

abstract class BaseUiState {

    val showProgress = MutableStateFlow<Boolean>(false)

    private val _showToast = Channel<String?>()
    val showError = _showToast.consumeAsFlow()

    fun showProgress() {
        showProgress.value = true
    }

    fun hideProgress() {
        showProgress.value = false
    }

    suspend fun sendToastMessage(message: String?) {
        _showToast.send(message)
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        data class Error(val message: String) : ScreenState()
        object Content : ScreenState()
    }
}