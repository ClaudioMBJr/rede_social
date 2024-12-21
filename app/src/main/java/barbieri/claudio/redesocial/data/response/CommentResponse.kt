package barbieri.claudio.redesocial.data.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CommentResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("texto")
    val text: String?,
    @SerializedName("data_hora")
    val date: Date?,
    @SerializedName("usuario_login")
    val login: String?,
    @SerializedName("usuario_nome")
    val name: String?,
    @SerializedName("usuario_foto")
    val userPhoto: String?,
) {
    companion object {
        fun mock() = CommentResponse(
            id = 1,
            text = "comentario",
            date = Date(),
            login = "login",
            name = "nome",
            userPhoto = "https://tinyurl.com/6w7rfev3"
        )
    }
}