package barbieri.claudio.commons.data.repository

import barbieri.claudio.commons.data.api.SocialIfesApi
import barbieri.claudio.commons.data.mapper.CommentMapper.toComment
import barbieri.claudio.commons.data.mapper.PostMapper.toPost
import barbieri.claudio.commons.data.mapper.RegisterRequestMapper.toRegisterRequest
import barbieri.claudio.commons.data.request.CommentRequest
import barbieri.claudio.commons.data.request.IdRequest
import barbieri.claudio.commons.data.request.LoginRequest
import barbieri.claudio.commons.domain.model.Comment
import barbieri.claudio.commons.domain.model.Post
import barbieri.claudio.commons.domain.model.Register
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SocialIfesRepositoryImpl @Inject constructor(private val api: SocialIfesApi) :
    SocialIfesRepository {

    override suspend fun login(login: String, password: String): Result<Unit> = safeDispatcher {
        api.login(LoginRequest(login = login, password = password))
    }


    override suspend fun register(register: Register): Result<Unit> = safeDispatcher {
        api.register(register.toRegisterRequest())
    }

    override suspend fun getPosts(
        limit: Int,
        offset: Int
    ): Result<List<Post>> = safeDispatcher {
        val response = api.getPosts(limit = limit, offset = offset)
        response.posts?.map { it.toPost() }.orEmpty()
    }

    override suspend fun getPost(postId: Int): Result<Post> = safeDispatcher {
        api.getPost(postId).toPost()
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
        api.like(IdRequest(postId))
    }

    override suspend fun unlike(postId: Int): Result<Unit> = safeDispatcher {
        api.unlike(IdRequest(postId))
    }

    override suspend fun follow(user: String): Result<Unit> = safeDispatcher {
        api.follow(user)
    }

    override suspend fun unfollow(user: String): Result<Unit> = safeDispatcher {
        api.unfollow(user)
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
