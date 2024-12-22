package barbieri.claudio.commons.data.request

import com.google.gson.annotations.SerializedName
import java.io.File

data class PostRequest(
    @SerializedName("texto")
    val text: String?,
    @SerializedName("imagem")
    val image: File?
)
