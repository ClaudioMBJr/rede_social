package barbieri.claudio.commons.data.repository

import barbieri.claudio.commons.data.api.SocialIfesApi
import barbieri.claudio.commons.data.mapper.CommentMapper.toComment
import barbieri.claudio.commons.data.mapper.PostMapper.toPost
import barbieri.claudio.commons.data.mapper.UserMapper.toUser
import barbieri.claudio.commons.data.request.CommentRequest
import barbieri.claudio.commons.data.request.IdRequest
import barbieri.claudio.commons.domain.model.Comment
import barbieri.claudio.commons.domain.model.Post
import barbieri.claudio.commons.domain.model.Register
import barbieri.claudio.commons.domain.model.User
import barbieri.claudio.commons.domain.model.UserAuth
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import barbieri.claudio.commons.preferences.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class SocialIfesRepositoryImpl @Inject constructor(
    private val api: SocialIfesApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) :
    SocialIfesRepository {

    override suspend fun login(login: String, password: String): Result<Unit> = safeDispatcher {
        val token = generateToken(login, password)
        api.login(token)
        sharedPreferencesManager.saveUser(
            UserAuth(
                login,
                password,
                token
            )
        )
    }

    private fun generateToken(login: String, password: String): String {
        return Credentials.basic(login, password)
    }

    override suspend fun register(register: Register): Result<Unit> = safeDispatcher {
        val loginRequestBody = register.login.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordRequestBody = register.password.toRequestBody("text/plain".toMediaTypeOrNull())
        val nameRequestBody = register.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val cityRequestBody = register.city.toRequestBody("text/plain".toMediaTypeOrNull())
        val birthDateRequestBody =
            register.birthDate.getBirthDate().toRequestBody("text/plain".toMediaTypeOrNull())

        val image = register.image?.let { file ->
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("foto", file.name, requestFile)
        }

        api.register(
            login = loginRequestBody,
            password = passwordRequestBody,
            name = nameRequestBody,
            city = cityRequestBody,
            birthDate = birthDateRequestBody,
            image = image
        )
    }

    private fun String.getBirthDate(): String {
        val numbersOnly = this.replace("[^0-9]".toRegex(), "")
        if (numbersOnly.length == 8) { // Ensure we have 8 digits for DDMMYYYY
            val inputFormat =
                SimpleDateFormat("ddMMyyyy", java.util.Locale.getDefault()) // Changed input format
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val date = inputFormat.parse(numbersOnly)
            return outputFormat.format(date ?: Date())
        }
        return ""
    }

    override suspend fun getPosts(
        limit: Int,
        offset: Int,
        user : String?
    ): Result<List<Post>> = safeDispatcher {
        val response = api.getPosts(limit = limit, offset = offset, user = user)
        response.posts?.map { it.toPost() }.orEmpty()
    }

    override suspend fun getPost(postId: Int): Result<Post> = safeDispatcher {
        api.getPost(postId).toPost()
    }

    override suspend fun post(
        image: File?,
        post: String?
    ): Result<Unit> = safeDispatcher {
        val textRequestBody =
            post?.toRequestBody("text/plain".toMediaTypeOrNull())

        val image = image?.let { file ->
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("imagem", file.name, requestFile)
        }

        api.post(
            text = textRequestBody,
            image = image
        )
    }

    override suspend fun getComments(
        postId: Int,
        limit: Int,
        offset: Int
    ): Result<List<Comment>> = safeDispatcher {
        api.getComments(
            limit = limit,
            offset = offset,
            id = postId
        ).comments?.map { it.toComment() }
            .orEmpty()
    }

    override suspend fun comment(
        postId: Int,
        text: String,
    ): Result<Unit> = safeDispatcher {
        api.comment(CommentRequest(id = postId, text = text))
    }

    override suspend fun like(postId: Int): Result<Unit> = safeDispatcher {
        api.like(postId.toString().toRequestBody("text/plain".toMediaTypeOrNull()))
    }

    override suspend fun unlike(postId: Int): Result<Unit> = safeDispatcher {
        api.unlike(postId.toString().toRequestBody("text/plain".toMediaTypeOrNull()))
    }

    override suspend fun follow(user: String): Result<Unit> = safeDispatcher {
        api.follow(user.toRequestBody("text/plain".toMediaTypeOrNull()))
    }

    override suspend fun unfollow(user: String): Result<Unit> = safeDispatcher {
        api.unfollow(user.toRequestBody("text/plain".toMediaTypeOrNull()))
    }

    override suspend fun search(user: String): Result<List<User>> = safeDispatcher {
        api.search(user).users?.map { it.toUser() }.orEmpty()
    }

    override suspend fun getUser(user: String): Result<User> = safeDispatcher {
        api.getUser(user).toUser()
    }

    suspend fun <T> safeDispatcher(call: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(call())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
