package barbieri.claudio.redesocial.data.mapper

import barbieri.claudio.redesocial.data.request.RegisterRequest
import barbieri.claudio.redesocial.domain.model.Register

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