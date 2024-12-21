package barbieri.claudio.redesocial.data.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class NotificationResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("data_hora")
    val date: Date?,
    @SerializedName("usuario_login")
    val login: String?,
    @SerializedName("acao")
    val action: Int?,
    @SerializedName("post_id")
    val postId: Int?,
) {
    companion object {
        fun mock() = NotificationResponse(
            id = 1,
            date = Date(),
            login = "login",
            action = 1,
            postId = 1
        )
    }
}