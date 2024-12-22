package barbieri.claudio.commons.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("sucesso")
    val success: Int?,
    @SerializedName("erro")
    val error: String?,
    @SerializedName("cod_erro")
    val errorCode: Int?,
    @SerializedName("usuarios")
    val users: List<UserResponse>?
) {
    companion object {
        fun mock() = SearchResponse(
            success = 1,
            error = null,
            errorCode = null,
            users = listOf(UserResponse.mock())
        )
    }
}