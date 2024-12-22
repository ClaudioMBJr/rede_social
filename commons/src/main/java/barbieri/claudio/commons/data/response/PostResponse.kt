package barbieri.claudio.commons.data.response

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
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
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            login = "login",
            name = "Usuário Fake",
            userPhoto = "https://cdn.pixabay.com/photo/2024/06/01/14/00/ai-8802304_1280.jpg",
            id = 1,
            image = "https://s2-techtudo.glbimg.com/L9wb1xt7tjjL-Ocvos-Ju0tVmfc=/0x0:1200x800/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2023/q/l/TIdfl2SA6J16XZAy56Mw/canvaai.png"
        )
    }
}
