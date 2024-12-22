package barbieri.claudio.commons.data.response

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
            image = "https://s2-techtudo.glbimg.com/L9wb1xt7tjjL-Ocvos-Ju0tVmfc=/0x0:1200x800/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2023/q/l/TIdfl2SA6J16XZAy56Mw/canvaai.png"
        )
    }
}
