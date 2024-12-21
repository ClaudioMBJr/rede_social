package barbieri.claudio.redesocial.data.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PhotoResponse(
    @SerializedName("post_id")
    val id: Int?,
    @SerializedName("foto")
    val photo: String?,
    @SerializedName("data_hora")
    val date: Date?
) {
    companion object {
        fun mock() = PhotoResponse(
            id = 1,
            photo = "https://tinyurl.com/6w7rfev3",
            date = Date()
        )
    }
}