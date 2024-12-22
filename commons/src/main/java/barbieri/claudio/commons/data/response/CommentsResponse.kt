package barbieri.claudio.commons.data.response

import com.google.gson.annotations.SerializedName

data class CommentsResponse(
    @SerializedName("sucesso")
    val success: Int?,
    @SerializedName("erro")
    val error: String?,
    @SerializedName("cod_erro")
    val errorCode: Int?,
    @SerializedName("n_comentarios")
    val commentsNumber: Int?,
    @SerializedName("usuarios")
    val comments: List<CommentResponse>?,
) {
    companion object {
        fun mock() = CommentsResponse(
            success = 1,
            error = null,
            errorCode = null,
            commentsNumber = 1,
            comments = listOf(CommentResponse.mock())
        )
    }
}