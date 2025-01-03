package barbieri.claudio.commons.data.mapper

import barbieri.claudio.commons.data.request.RegisterRequest
import barbieri.claudio.commons.domain.model.Register
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

object RegisterRequestMapper {

//    fun Register.toRegisterRequest() = RegisterRequest(
//        login = login,
//        password = password,
//        name = name,
//        city = city,
//        birthDate = birthDate.getBirthDate(),
//        image = image?.readText(charset("UTF-8"))
//    )


}