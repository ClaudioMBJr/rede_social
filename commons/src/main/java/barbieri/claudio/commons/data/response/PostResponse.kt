package barbieri.claudio.commons.data.response

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
    val date: String?,
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
            date = "2025-01-06 15:33:16",
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            login = "login",
            name = "Usuário Fake",
            userPhoto = "https://s2-techtudo.glbimg.com/4uvIHOMReah_7syBff5VD9rnkV4=/0x0:1184x896/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2024/a/k/6T2EyXSNmezAkYcsNh9g/leonardoai.jpg",
            id = 1,
            image = "https://s2-techtudo.glbimg.com/4uvIHOMReah_7syBff5VD9rnkV4=/0x0:1184x896/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2024/a/k/6T2EyXSNmezAkYcsNh9g/leonardoai.jpg"
        )
    }
}
