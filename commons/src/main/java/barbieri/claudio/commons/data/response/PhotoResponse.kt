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
            photo = "https://s2-techtudo.glbimg.com/L9wb1xt7tjjL-Ocvos-Ju0tVmfc=/0x0:1200x800/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2023/q/l/TIdfl2SA6J16XZAy56Mw/canvaai.png",
            date = Date()
        )
    }
}