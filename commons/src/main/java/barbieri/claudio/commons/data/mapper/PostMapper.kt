package barbieri.claudio.commons.data.mapper

import barbieri.claudio.commons.data.response.PostResponse
import barbieri.claudio.commons.domain.model.Post
import java.util.Date

object PostMapper {

    fun PostResponse.toPost() = Post(
        date = date.orEmpty(),
        text = text.orEmpty(),
        login = login.orEmpty(),
        name = name.orEmpty(),
        userPhoto = userPhoto.orEmpty(),
        id = id ?: 0,
        image = image.orEmpty()
    )
}