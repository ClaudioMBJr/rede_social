package barbieri.claudio.redesocial.data.response

import com.google.gson.annotations.SerializedName

data class NotificationsResponse(
    @SerializedName("sucesso")
    val success: Int?,
    @SerializedName("erro")
    val error: String?,
    @SerializedName("cod_erro")
    val errorCode: Int?,
    @SerializedName("notificacoes")
    val notifications: List<NotificationResponse>?,
) {
    companion object {
        fun mock() = NotificationsResponse(
            success = 1,
            error = null,
            errorCode = null,
            notifications = listOf(NotificationResponse.mock())
        )
    }
}