package barbieri.claudio.commons.data.mapper

import barbieri.claudio.commons.data.request.RegisterRequest
import barbieri.claudio.commons.domain.model.Register

object RegisterRequestMapper {

    fun Register.toRegisterRequest() = RegisterRequest(
        login = login,
        password = password,
        name = name,
        city = city,
        birthDate = birthDate,
        image = image
    )
}