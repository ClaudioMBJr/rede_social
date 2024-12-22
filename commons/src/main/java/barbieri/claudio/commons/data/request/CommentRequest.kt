package barbieri.claudio.commons.data.request

import com.google.gson.annotations.SerializedName

data class CommentRequest(
    @SerializedName("idpost")
    val id: Int,
    @SerializedName("comentario")
    val text: String?
)
