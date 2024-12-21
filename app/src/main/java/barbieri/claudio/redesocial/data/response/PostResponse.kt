package barbieri.claudio.redesocial.data.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PostResponse(
    @SerializedName("sucesso")
    val success: Int?,
    @SerializedName("erro")
    val error: String?,
    @SerializedName("cod_erro")
    val errorCode: Int?,
    @SerializedName("data_hora")
    val date: Date?,
    @SerializedName("texto")
    val text: String?,
    @SerializedName("usuario_login")
    val login: String?,
    @SerializedName("usuario_nome")
    val name: String?,
    @SerializedName("usuario_foto")
    val userPhoto: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imagem")
    val image: String?
) {
    companion object {
        fun mock() = PostResponse(
            success = 1,
            error = null,
            errorCode = null,
            date = Date(),
            text = "texto",
            login = "login",
            name = "nome",
            userPhoto = "https://tinyurl.com/6w7rfev3",
            id = 1,
            image = "https://tinyurl.com/6w7rfev3"
        )
    }
}
