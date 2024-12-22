package barbieri.claudio.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barbieri.claudio.commons.components.BaseUiState
import barbieri.claudio.commons.domain.model.Post
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SocialIfesRepository,
) : ViewModel() {

    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

    val uiState = UiState()

    init {
        getPosts()
    }

    fun getPosts(getMore: Boolean = false) {
        viewModelScope.launch {
            if (getMore) uiState.updateLimitAndOffset()
            uiState.showProgress()
            repository.getPosts(uiState.currentLimit, uiState.currentOffset).fold(
                onSuccess = {
                    uiState.setPosts(it)
                },
                onFailure = {
                    uiState.sendToastMessage(it.message.orEmpty())
                }
            )
        }
    }

    sealed class ScreenEvent {

    }

    class UiState : BaseUiState() {
        val posts = MutableStateFlow<MutableList<Post>>(mutableStateListOf())
        var currentLimit: Int = LIMIT
        var currentOffset: Int = ZERO

        fun setPosts(posts: List<Post>) {
            this.posts.value += posts
            hideProgress()
        }

        fun updateLimitAndOffset() {
            currentOffset = currentLimit
            currentLimit = currentLimit + LIMIT
        }

        companion object {
            private const val LIMIT = 10
            private const val ZERO = 0
        }
    }
}