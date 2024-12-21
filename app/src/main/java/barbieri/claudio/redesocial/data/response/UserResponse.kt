package barbieri.claudio.redesocial.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("login")
    val login: String?,
    @SerializedName("nome")
    val name: String?,
    @SerializedName("cidade")
    val city: String?,
    @SerializedName("data_nascimento")
    val birthDate: String?,
    @SerializedName("foto")
    val image: String?,
    @SerializedName("esta_sendo_seguido")
    val following: Boolean?
) {
    companion object {
        fun mock() = UserResponse(
            login = "login",
            name = "nome",
            city = "cidade",
            birthDate = "1997-10-19",
            image = "https://tinyurl.com/6w7rfev3",
            following = true
        )
    }
}