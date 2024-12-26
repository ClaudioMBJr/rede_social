package barbieri.claudio.commons.data.response

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
            userPhoto = "https://s2-techtudo.glbimg.com/4uvIHOMReah_7syBff5VD9rnkV4=/0x0:1184x896/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2024/a/k/6T2EyXSNmezAkYcsNh9g/leonardoai.jpg"
        )
    }
}