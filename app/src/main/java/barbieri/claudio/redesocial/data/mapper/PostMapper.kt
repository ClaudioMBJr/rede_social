package barbieri.claudio.redesocial.data.mapper

import barbieri.claudio.redesocial.data.response.PostResponse
import barbieri.claudio.redesocial.domain.model.Post
import java.util.Date

object PostMapper {

    fun PostResponse.toPost() = Post(
        date = date ?: Date(),
        text = text.orEmpty(),
        login = login.orEmpty(),
        name = name.orEmpty(),
        userPhoto = userPhoto.orEmpty(),
        id = id ?: 0,
        image = image.orEmpty()
    )
}