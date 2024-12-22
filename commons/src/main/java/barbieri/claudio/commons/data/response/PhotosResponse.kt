package barbieri.claudio.commons.data.response

import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    @SerializedName("sucesso")
    val success: Int?,
    @SerializedName("erro")
    val error: String?,
    @SerializedName("cod_erro")
    val errorCode: Int?,
    @SerializedName("n_fotos")
    val photosNumber: Int?,
    @SerializedName("fotos")
    val photos: List<PhotoResponse?>?,
) {
    fun mock() = PhotosResponse(
        success = 1,
        error = null,
        errorCode = null,
        photos = listOf(PhotoResponse.mock()),
        photosNumber = 1
    )
}