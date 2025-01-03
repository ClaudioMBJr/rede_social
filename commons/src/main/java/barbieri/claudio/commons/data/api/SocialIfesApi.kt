package barbieri.claudio.commons.data.api

import android.R
import barbieri.claudio.commons.data.request.CommentRequest
import barbieri.claudio.commons.data.request.IdRequest
import barbieri.claudio.commons.data.request.RegisterRequest
import barbieri.claudio.commons.data.response.CommentsResponse
import barbieri.claudio.commons.data.response.CommonResponse
import barbieri.claudio.commons.data.response.FollowingResponse
import barbieri.claudio.commons.data.response.PostResponse
import barbieri.claudio.commons.data.response.PostsResponse
import barbieri.claudio.commons.data.response.SearchResponse
import barbieri.claudio.commons.data.response.SearchUserResponse
import barbieri.claudio.commons.data.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface SocialIfesApi {

    @POST("login.php")
    suspend fun login(
        @Header("Authorization") authHeader: String
    ): CommonResponse

    @Multipart
    @POST("cadastra_usuario.php")
    suspend fun register(
        @Part("login") login: RequestBody,
        @Part("senha") password: RequestBody,
        @Part("nome") name: RequestBody,
        @Part("cidade") city: RequestBody,
        @Part("data_nascimento") birthDate: RequestBody,
        @Part image: MultipartBody.Part?,
    ): CommonResponse

    @GET("buscar_usuario.php")
    suspend fun search(
        @Query("busca") query: String,
    ): SearchResponse

    @POST("atualizar_usuario.php")
    suspend fun update(
        @Body updateRequest: RegisterRequest
    ): CommonResponse

    @GET("pegar_usuario.php")
    suspend fun getUser(
        @Query("login") query: String
    ): UserResponse

    @Multipart
    @POST("seguir.php")
    suspend fun follow(
        @Part("usuario") user : RequestBody
    ): CommonResponse

    @Multipart
    @POST("desfazer_seguir.php")
    suspend fun unfollow(
        @Part("usuario") user : RequestBody
    ): CommonResponse

    @GET("pegar_seguindo.php")
    suspend fun getFollowing(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("usuario_login") user: String
    ): FollowingResponse

    @Multipart
    @POST("post.php")
    suspend fun post(
        @Part("texto") text: RequestBody?,
        @Part image: MultipartBody.Part?,
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

    @Multipart
    @POST("curtir.php")
    suspend fun like(
        @Part("post_id") idRequest: RequestBody,
    ): CommonResponse

    @Multipart
    @POST("descutir.php")
    suspend fun unlike(
        @Body idRequest: RequestBody,
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