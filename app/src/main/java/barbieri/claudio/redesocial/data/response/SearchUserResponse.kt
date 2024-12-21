package barbieri.claudio.redesocial.data.response

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("sucesso")
    val success: Int?,
    @SerializedName("erro")
    val error: String?,
    @SerializedName("cod_erro")
    val errorCode: Int?,
    @SerializedName("login")
    val login: String?,
    @SerializedName("nome")
    val name: String?,
    @SerializedName("cidade")
    val city: String?,
    @SerializedName("data_nascimento")
    val birthDate: String?,
    @SerializedName("foto")
    val image: String?
) {
    companion object {
        fun mock() = SearchUserResponse(
            success = 1,
            error = null,
            errorCode = null,
            login = "login",
            name = "nome",
            city = "cidade",
            birthDate = "1997-10-19",
            image = "https://tinyurl.com/6w7rfev3"
        )
    }
}
