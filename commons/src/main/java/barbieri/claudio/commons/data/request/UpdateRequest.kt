package barbieri.claudio.commons.data.request

import com.google.gson.annotations.SerializedName
import java.io.File

data class UpdateRequest(
    @SerializedName("nome")
    val name: String?,
    @SerializedName("cidade")
    val city: String?,
    @SerializedName("data_nascimento")
    val birthDate: String?,
    @SerializedName("foto")
    val image: File?,
    @SerializedName("senha")
    val password: String?
)