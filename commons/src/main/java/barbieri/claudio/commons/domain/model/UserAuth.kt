package barbieri.claudio.commons.domain.model

import java.io.Serializable

data class UserAuth(
    val login: String,
    val password: String
) : Serializable {
    companion object {
        fun mock() = UserAuth("login", "password")
    }
}