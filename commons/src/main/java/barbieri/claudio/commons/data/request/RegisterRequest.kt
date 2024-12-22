package barbieri.claudio.commons.data.request

import com.google.gson.annotations.SerializedName
import java.io.File

data class RegisterRequest(
    @SerializedName("login")
    val login: String,
    @SerializedName("senha")
    val password: String,
    @SerializedName("nome")
    val name: String,
    @SerializedName("cidade")
    val city: String,
    @SerializedName("data_nascimento")
    val birthDate: String,
    @SerializedName("foto")
    val image: File?
) {
    companion object {
        fun mock() = RegisterRequest(
            login = "login",
            password = "senha",
            name = "nome",
            city = "cidade",
            birthDate = "1997-10-19",
            image = null
        )
    }
}