package barbieri.claudio.redesocial.domain.repository

import barbieri.claudio.redesocial.domain.model.Post
import barbieri.claudio.redesocial.domain.model.Register

interface SocialIfesRepository {

    suspend fun login(login: String, password: String)

    suspend fun register(register: Register)

    suspend fun getPosts(limit: Int, offset: Int): List<Post>
}