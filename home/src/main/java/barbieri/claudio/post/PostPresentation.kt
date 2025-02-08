package barbieri.claudio.post

import barbieri.claudio.commons.domain.model.Post
import java.util.Date

data class PostPresentation(
    val date: String,
    val text: String,
    val login: String,
    val name: String,
    val userPhoto: String,
    val id: Int,
    val image: String,
    val isLike: Boolean,
    val isFollowing: Boolean
)


object PostPresentationMapper {
    fun Post.toPostPresentation() = PostPresentation(
        date = date,
        text = text,
        login = login,
        name = name,
        userPhoto = userPhoto,
        id = id,
        image = image,
        isLike = false,
        isFollowing = false
    )
}