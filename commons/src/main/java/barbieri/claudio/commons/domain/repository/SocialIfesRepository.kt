package barbieri.claudio.commons.domain.repository

import barbieri.claudio.commons.domain.model.Comment
import barbieri.claudio.commons.domain.model.Post
import barbieri.claudio.commons.domain.model.Register

interface SocialIfesRepository {

    suspend fun login(login: String, password: String): Result<Unit>

    suspend fun register(register: Register): Result<Unit>

    suspend fun getPosts(limit: Int, offset: Int): Result<List<Post>>

    suspend fun getPost(postId: Int): Result<Post>

    suspend fun getComments(postId: Int, limit: Int, offset: Int): Result<List<Comment>>

    suspend fun comment(postId: Int, text : String): Result<Unit>

    suspend fun like(postId: Int): Result<Unit>

    suspend fun unlike(postId: Int): Result<Unit>

    suspend fun follow(user: String): Result<Unit>

    suspend fun unfollow(user: String): Result<Unit>
}