package barbieri.claudio.commons.data.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("login")
    val login: String,
    @SerializedName("senha")
    val password: String
)
