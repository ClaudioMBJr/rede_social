package barbieri.claudio.commons.data.response

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("sucesso")
    val success: Int?,
    @SerializedName("mensagem")
    val message: String?,
    @SerializedName("erro")
    val error: String?,
    @SerializedName("cod_erro")
    val errorCode: Int?,
) {
    companion object {
        fun mock() = CommonResponse(
            success = 1,
            message = null,
            error = null,
            errorCode = null
        )
    }
}