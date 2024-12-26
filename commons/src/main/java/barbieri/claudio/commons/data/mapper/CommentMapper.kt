package barbieri.claudio.commons.data.mapper

import barbieri.claudio.commons.data.response.CommentResponse
import barbieri.claudio.commons.domain.model.Comment
import java.util.Date

object CommentMapper {

    fun CommentResponse.toComment() = Comment(
        id = id ?: 0,
        text = text.orEmpty(),
        date = date ?: Date(),
        login = login.orEmpty(),
        name = name.orEmpty(),
        userPhoto = userPhoto.orEmpty()
    )

}
