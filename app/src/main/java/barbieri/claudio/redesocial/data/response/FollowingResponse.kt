package barbieri.claudio.redesocial.data.response

import com.google.gson.annotations.SerializedName

data class FollowingResponse(
    @SerializedName("sucesso")
    val success: Int?,
    @SerializedName("erro")
    val error: String?,
    @SerializedName("cod_erro")
    val errorCode: Int?,
    @SerializedName("n_usuarios")
    val following: Int?,
    @SerializedName("usuarios")
    val users: List<UserResponse?>?,
) {
    companion object {
        fun mock() = FollowingResponse(
            success = 1,
            error = null,
            errorCode = null,
            following = 1,
            users = listOf(UserResponse.mock())
        )
    }
}