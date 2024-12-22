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
            userPhoto = "https://s2-techtudo.glbimg.com/L9wb1xt7tjjL-Ocvos-Ju0tVmfc=/0x0:1200x800/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2023/q/l/TIdfl2SA6J16XZAy56Mw/canvaai.png"
        )
    }
}