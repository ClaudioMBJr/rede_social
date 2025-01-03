package barbieri.claudio

import barbieri.claudio.commons.domain.model.User
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object Home

    @Serializable
    object CreatePost

    @Serializable
    data class Post(val postId: Int)

    @Serializable
    object Search

    @Serializable
    data class Profile(val login : String)
}