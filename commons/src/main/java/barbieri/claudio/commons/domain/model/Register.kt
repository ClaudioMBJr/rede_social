package barbieri.claudio.commons.domain.model

import java.io.File

data class Register(
    val login: String,
    val password: String,
    val name: String,
    val city: String,
    val birthDate: String,
    val image: File?
) {
    companion object {
        fun mock() = Register(
            login = "login",
            password = "senha",
            name = "nome",
            city = "cidade",
            birthDate = "1997-10-19",
            image = null
        )
    }
}