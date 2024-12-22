package barbieri.claudio.commons.data.api

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import barbieri.claudio.commons.data.request.CommentRequest
import barbieri.claudio.commons.data.request.IdRequest
import barbieri.claudio.commons.data.request.LoginRequest
import barbieri.claudio.commons.data.request.RegisterRequest
import barbieri.claudio.commons.data.response.CommonResponse
import barbieri.claudio.commons.data.response.FollowingResponse
import barbieri.claudio.commons.data.response.PostResponse
import barbieri.claudio.commons.data.response.PostsResponse
import barbieri.claudio.commons.data.response.SearchResponse
import barbieri.claudio.commons.data.response.SearchUserResponse

class SocialIfesApiMock : SocialIfesApi {

    override suspend fun login(loginRequest: LoginRequest): CommonResponse = CommonResponse.mock()

    override suspend fun register(registerRequest: RegisterRequest) = CommonResponse.mock()

    override suspend fun search(query: String): SearchResponse = SearchResponse.mock()

    override suspend fun update(updateRequest: RegisterRequest): CommonResponse =
        CommonResponse.mock()

    override suspend fun getUser(query: String) = SearchUserResponse.mock()

    override suspend fun follow(query: String) = CommonResponse.mock()

    override suspend fun unfollow(query: String) = CommonResponse.mock()

    override suspend fun getFollowing(
        limit: Int,
        offset: Int,
        user: String
    ): FollowingResponse = FollowingResponse.mock()

    override suspend fun post(postRequest: RegisterRequest): CommonResponse = CommonResponse.mock()

    override suspend fun getPost(id: Int): PostResponse = PostResponse.mock()

    override suspend fun getPosts(
        limit: Int,
        offset: Int,
        user: String?
    ): PostsResponse = PostsResponse.mock().copy(
        posts = listOf(
            PostResponse.mock(),
            PostResponse.mock(),
            PostResponse.mock(),
            PostResponse.mock(),
            PostResponse.mock()
        )
    )

    override suspend fun comment(commentRequest: CommentRequest): CommonResponse =
        CommonResponse.mock()

    override suspend fun like(idRequest: IdRequest): CommonResponse = CommonResponse.mock()

    override suspend fun unlike(idRequest: IdRequest): CommonResponse = CommonResponse.mock()

    override suspend fun deletePost(idRequest: IdRequest): CommonResponse = CommonResponse.mock()

    override suspend fun getComments(
        limit: Int,
        offset: Int,
        id: String
    ): CommonResponse = CommonResponse.mock()

    override suspend fun getPhotos(
        limit: Int,
        offset: Int,
        login: String
    ): CommonResponse = CommonResponse.mock()

    override suspend fun getNotifications(
        limit: Int,
        offset: Int,
        news: String
    ): CommonResponse = CommonResponse.mock()

}