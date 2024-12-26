package barbieri.claudio.commons.data.response

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
            photo = "https://s2-techtudo.glbimg.com/4uvIHOMReah_7syBff5VD9rnkV4=/0x0:1184x896/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2024/a/k/6T2EyXSNmezAkYcsNh9g/leonardoai.jpg",
            date = Date()
        )
    }
}