package barbieri.claudio.commons.data.api

import barbieri.claudio.commons.data.request.CommentRequest
import barbieri.claudio.commons.data.request.IdRequest
import barbieri.claudio.commons.data.request.LoginRequest
import barbieri.claudio.commons.data.request.RegisterRequest
import barbieri.claudio.commons.data.response.CommentsResponse
import barbieri.claudio.commons.data.response.CommonResponse
import barbieri.claudio.commons.data.response.FollowingResponse
import barbieri.claudio.commons.data.response.PostResponse
import barbieri.claudio.commons.data.response.PostsResponse
import barbieri.claudio.commons.data.response.SearchResponse
import barbieri.claudio.commons.data.response.SearchUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SocialIfesApi {

    @POST("login.php")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): CommonResponse

    @POST("cadastra_usuario.php")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): CommonResponse

    @GET("busca_usuario.php")
    suspend fun search(
        @Query("busca") query: String
    ): SearchResponse

    @POST("atualizar_usuario.php")
    suspend fun update(
        @Body updateRequest: RegisterRequest
    ): CommonResponse

    @GET("pegar_usuario.php")
    suspend fun getUser(
        @Query("login") query: String
    ): SearchUserResponse

    @POST("seguir.php")
    suspend fun follow(
        @Query("usuario") query: String
    ): CommonResponse

    @POST("desfazer_seguir.php")
    suspend fun unfollow(
        @Query("usuario") query: String
    ): CommonResponse

    @GET("pegar_seguindo.php")
    suspend fun getFollowing(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("usuario_login") user: String
    ): FollowingResponse

    @POST("post.php")
    suspend fun post(
        @Body postRequest: RegisterRequest
    ): CommonResponse

    @GET("pegar_post.php")
    suspend fun getPost(
        @Query("id") id: Int
    ): PostResponse

    @GET("pegar_posts.php")
    suspend fun getPosts(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("usuario") user: String? = null
    ): PostsResponse

    @POST("comentar.php")
    suspend fun comment(
        @Body commentRequest: CommentRequest,
    ): CommonResponse

    @POST("curtir.php")
    suspend fun like(
        @Body idRequest: IdRequest,
    ): CommonResponse

    @POST("descutir.php")
    suspend fun unlike(
        @Body idRequest: IdRequest,
    ): CommonResponse

    @POST("excluir_post.php")
    suspend fun deletePost(
        @Body idRequest: IdRequest,
    ): CommonResponse

    @GET("pegar_comentarios.php")
    suspend fun getComments(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("post_id") id: Int
    ): CommentsResponse

    @GET("pegar_galeria.php")
    suspend fun getPhotos(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("usuario_login") login: String
    ): CommonResponse

    @GET("pegar_notificacoes.php")
    suspend fun getNotifications(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("somente_novas") news: String
    ): CommonResponse

}