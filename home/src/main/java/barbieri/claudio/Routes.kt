package barbieri.claudio

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object Home

    @Serializable
    data class Post(val postId: Int)
}