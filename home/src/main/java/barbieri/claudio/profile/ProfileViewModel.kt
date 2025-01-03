package barbieri.claudio.profile

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barbieri.claudio.commons.components.BaseUiState
import barbieri.claudio.commons.domain.model.Post
import barbieri.claudio.commons.domain.model.User
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: SocialIfesRepository
) : ViewModel() {

    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

    private var login: String = ""

    val uiState = UiState()

    fun setup(login: String) {
        this.login = login
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            uiState.showProgress()
            repository.getUser(login).fold(
                onSuccess = {
                    uiState.setUser(it)
                    getPosts()
                },
                onFailure = {
                    uiState.sendToastMessage(it.message.orEmpty())
                }
            )
        }
    }

    private fun getPosts(getMore: Boolean = false) {
        viewModelScope.launch {
            if (getMore) uiState.updateLimitAndOffset()
            uiState.showProgress()
            repository.getPosts(uiState.currentLimit, uiState.currentOffset, login).fold(
                onSuccess = {
                    uiState.setPosts(it)
                },
                onFailure = {
                    uiState.sendToastMessage(it.message.orEmpty())
                }
            )
        }
    }

    fun follow() {
        viewModelScope.launch {
            uiState.showProgress()
            try {
                if (uiState.getIsFollowing()) {
                    repository.unfollow(user = login).getOrThrow()
                    uiState.unfollow()
                } else {
                    repository.follow(user = login).exceptionOrNull()
                    uiState.follow()
                }
            } catch (e: Throwable) {
                uiState.sendToastMessage(e.message.orEmpty())
            }
            uiState.hideProgress()
        }
    }

    fun navigateToPost(postId: Int) =
        viewModelScope.launch { _event.send(ScreenEvent.NavigateToPost(postId)) }

    sealed class ScreenEvent {
        data class NavigateToPost(val postId: Int) : ScreenEvent()
    }

    class UiState : BaseUiState() {
        val posts = MutableStateFlow<MutableList<Post>>(mutableStateListOf())
        val user = MutableStateFlow<User?>(null)
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

        fun setUser(user: User) {
            this.user.value = user
        }

        fun follow() {
            this.user.update { it?.copy(following = true) }
        }

        fun unfollow() {
            this.user.update { it?.copy(following = false) }
        }

        fun getIsFollowing() = this.user.value?.following == true

        companion object {
            private const val LIMIT = 10
            private const val ZERO = 0
        }
    }
}