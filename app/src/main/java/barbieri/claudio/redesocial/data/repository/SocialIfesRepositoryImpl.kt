package barbieri.claudio.redesocial.data.repository

import barbieri.claudio.redesocial.data.api.SocialIfesApi
import barbieri.claudio.redesocial.data.mapper.PostMapper.toPost
import barbieri.claudio.redesocial.data.mapper.RegisterRequestMapper.toRegisterRequest
import barbieri.claudio.redesocial.data.request.LoginRequest
import barbieri.claudio.redesocial.domain.model.Post
import barbieri.claudio.redesocial.domain.model.Register
import barbieri.claudio.redesocial.domain.repository.SocialIfesRepository
import javax.inject.Inject

class SocialIfesRepositoryImpl @Inject constructor(private val api: SocialIfesApi) :
    SocialIfesRepository {

    override suspend fun login(login: String, password: String) {
        val response = api.login(LoginRequest(login = login, password = password))
        if (response.success != SUCCESS_CODE) {
            throw CustomException(response.error)
        }
    }

    override suspend fun register(register: Register) {
        val response = api.register(register.toRegisterRequest())
        if (response.success != SUCCESS_CODE) {
            throw CustomException(response.error)
        }
    }

    override suspend fun getPosts(
        limit: Int,
        offset: Int
    ): List<Post> {
        val response = api.getPosts(limit = limit, offset = offset)
        return if (response.success != SUCCESS_CODE) {
            throw CustomException(response.error)
        } else response.posts?.map { it.toPost() }.orEmpty()
    }

    companion object {
        private const val SUCCESS_CODE = 1
    }
}
