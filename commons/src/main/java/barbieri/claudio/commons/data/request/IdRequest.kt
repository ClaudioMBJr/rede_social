package barbieri.claudio.commons.data.request

import com.google.gson.annotations.SerializedName

data class IdRequest(
    @SerializedName("post_id")
    val id: Int?
)
