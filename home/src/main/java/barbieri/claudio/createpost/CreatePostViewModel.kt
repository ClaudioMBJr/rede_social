package barbieri.claudio.createpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barbieri.claudio.commons.components.BaseUiState
import barbieri.claudio.commons.domain.model.Register
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import barbieri.claudio.post.PostViewModel.ScreenEvent
import barbieri.claudio.post.PostViewModel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(private val repository: SocialIfesRepository) :
    ViewModel() {

    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

    val uiState = UiState()

    fun post() {
        viewModelScope.launch {
            uiState.showProgress()
            repository.post(image = uiState.getImage(), post = uiState.getPost()).fold(
                onSuccess = { _event.send(CreatePostViewModel.ScreenEvent.GoBack) },
                onFailure = { uiState.sendToastMessage(it.message.orEmpty()) }
            )
            uiState.hideProgress()
        }
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
    }


    class UiState : BaseUiState() {
        val post = MutableStateFlow("")
        val image = MutableStateFlow<File?>(null)

        fun setImage(image: File?) {
            this.image.value = image
        }

        fun setPost(post: String) {
            this.post.value = post
        }

        fun getImage() = image.value

        fun getPost() = post.value

    }
}