package barbieri.claudio.commons.di

import barbieri.claudio.commons.data.api.SocialIfesApi
import barbieri.claudio.commons.data.api.SocialIfesApiMock
import barbieri.claudio.commons.data.repository.SocialIfesRepositoryImpl
import barbieri.claudio.commons.domain.model.UserAuth
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import barbieri.claudio.commons.preferences.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApiService(
        sharedPreferencesManager: SharedPreferencesManager
    ): SocialIfesApi {
        return if (true) {
            Retrofit.Builder()
                .baseUrl("https://socialifesweb-bojnmat9.b4a.run/")
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(
                            object : Interceptor {
                                override fun intercept(chain: Interceptor.Chain): Response {
                                    val user = sharedPreferencesManager.getUser()
                                    val originalRequest = chain.request()
                                    val request = if (user != null) {
                                        originalRequest.newBuilder()
                                            .header("Authorization", user.token)
                                            .build()
                                    } else originalRequest

                                    val response = chain.proceed(request)


                                    val errorMessage = handleResponse(response)

                                    //Verificar melhor forma de retornar uma exception em caso de erro (sucesso != 1)
                                    return if (errorMessage != null) {
                                        Response.Builder()
                                            .request(request)
                                            .protocol(Protocol.HTTP_1_1)
                                            .code(400)
                                            .body("{error: '$errorMessage'}".toResponseBody(null))
                                            .message(errorMessage)
                                            .build()
                                    } else response
                                }
                            }
                        )
                        .addInterceptor(
                            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        )
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SocialIfesApi::class.java)
        } else SocialIfesApiMock()
    }

    private fun handleResponse(response: Response): String? {
        return try {
            response.body?.let {
                val source = it.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer
                val responseBodyString = buffer.clone().readString(Charsets.UTF_8)
                val regex = "(\\{.*?\\})".toRegex()
                val matchResult = regex.find(responseBodyString)
                val contentInsideBraces = matchResult?.groupValues?.get(1)
                val jsonResponse = JSONObject(contentInsideBraces.orEmpty())
                val code = jsonResponse.get(SUCCESS_KEY)
                if (code != SUCCESS_CODE) jsonResponse.get(ERROR_KEY).toString()
                else null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @Provides
    @Singleton
    fun providesRepository(api: SocialIfesApi, prefs : SharedPreferencesManager): SocialIfesRepository =
        SocialIfesRepositoryImpl(api, prefs)

    private const val SUCCESS_CODE = "1"
    private const val SUCCESS_KEY = "sucesso"
    private const val ERROR_KEY = "erro"
}