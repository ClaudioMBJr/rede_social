package barbieri.claudio.post

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barbieri.claudio.commons.components.BaseUiState
import barbieri.claudio.commons.domain.model.Comment
import barbieri.claudio.commons.domain.model.Post
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import barbieri.claudio.post.PostPresentationMapper.toPostPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val repository: SocialIfesRepository) :
    ViewModel() {

    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

    private var postId = 0

    private var user = ""

    val uiState = UiState()

    fun setPostId(postId : Int) {
        this.postId = postId
        getPost(postId)
    }

    fun getPost(postId: Int) {
        this.postId = postId
        viewModelScope.launch {
            uiState.showProgress()
            val post = async { repository.getPost(postId).getOrThrow() }.await()
            val comments = async {
                repository.getComments(
                    postId,
                    uiState.currentLimit,
                    uiState.currentOffset
                ).getOrThrow()
            }.await()

            try {
                uiState.apply {
                    user = post.name
                    setPost(post)
                    setComments(comments)
                }
            } catch (e: Throwable) {
                uiState.sendToastMessage(e.message.orEmpty())
            }
            uiState.hideProgress()
        }
    }

    fun comment() {
        viewModelScope.launch {
            uiState.showProgress()
            repository.comment(postId = postId, text = uiState.getText()).fold(
                onSuccess = { getPost(postId) },
                onFailure = { uiState.sendToastMessage(it.message.orEmpty()) }
            )
            uiState.hideProgress()
        }
    }

    fun like() {
        viewModelScope.launch {
            uiState.showProgress()
            try {
                if (uiState.getIsLiked()) {
                    repository.unlike(postId).getOrThrow()
                    uiState.unlike()
                } else {
                    repository.like(postId).exceptionOrNull()
                    uiState.like()
                }
            } catch (e: Throwable) {
                uiState.sendToastMessage(e.message.orEmpty())
            }
            uiState.hideProgress()
        }
    }

    fun follow() {
        viewModelScope.launch {
            uiState.showProgress()
            try {
                if (uiState.getIsFollowing()) {
                    repository.unfollow(user = user).getOrThrow()
                    uiState.unfollow()
                } else {
                    repository.follow(user = user).exceptionOrNull()
                    uiState.follow()
                }
            } catch (e: Throwable) {
                uiState.sendToastMessage(e.message.orEmpty())
            }
            uiState.hideProgress()
        }
    }


    sealed class ScreenEvent {
    }

    class UiState : BaseUiState() {
        val post = MutableStateFlow<PostPresentation?>(null)
        val comments = MutableStateFlow<List<Comment>>(emptyList())
        val comment = MutableStateFlow<TextFieldValue>(TextFieldValue())

        var currentLimit: Int = LIMIT
        var currentOffset: Int = ZERO

        fun setPost(post: Post) {
            this.post.value = post.toPostPresentation()
        }

        fun setComments(comment: List<Comment>) {
            this.comments.value = comment
        }

        fun like() {
            post.value = post.value?.copy(isLike = true)
        }

        fun unlike() {
            post.value = post.value?.copy(isLike = false)
        }

        fun follow() {
            post.value = post.value?.copy(isFollowing = true)
        }

        fun unfollow() {
            post.value = post.value?.copy(isFollowing = false)
        }

        fun updateComment(text: TextFieldValue) {
            comment.value = text
        }

        fun getText() = comment.value.text

        fun getIsLiked() = post.value?.isLike == true

        fun getIsFollowing() = post.value?.isFollowing == true

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