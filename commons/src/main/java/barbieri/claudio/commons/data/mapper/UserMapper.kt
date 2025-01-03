package barbieri.claudio.commons.data.mapper

import barbieri.claudio.commons.data.response.UserResponse
import barbieri.claudio.commons.domain.model.User

object UserMapper {

    fun UserResponse.toUser() = User(
        login = login.orEmpty(),
        name = name.orEmpty(),
        city = city.orEmpty(),
        birthDate = birthDate.orEmpty(),
        image = image.orEmpty(),
        following = following == true
    )
}