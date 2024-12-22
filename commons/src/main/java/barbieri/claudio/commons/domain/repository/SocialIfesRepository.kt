package barbieri.claudio.commons.domain.repository

import barbieri.claudio.commons.domain.model.Post
import barbieri.claudio.commons.domain.model.Register

interface SocialIfesRepository {

    suspend fun login(login: String, password: String): Result<Unit>

    suspend fun register(register: Register): Result<Unit>

    suspend fun getPosts(limit: Int, offset: Int): Result<List<Post>>
}