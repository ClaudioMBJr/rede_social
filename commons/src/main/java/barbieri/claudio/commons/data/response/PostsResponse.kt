package barbieri.claudio.commons.data.response

import com.google.gson.annotations.SerializedName

data class PostsResponse(
    @SerializedName("sucesso")
    val success: Int?,
    @SerializedName("erro")
    val error: String?,
    @SerializedName("cod_erro")
    val errorCode: Int?,
    @SerializedName("n_posts")
    val postsNumber: Int?,
    @SerializedName("posts")
    val posts: List<PostResponse>?
) {
    companion object {
        fun mock() = PostsResponse(
            success = 1,
            error = null,
            errorCode = null,
            postsNumber = 1,
            posts = listOf(PostResponse.mock())
        )
    }
}