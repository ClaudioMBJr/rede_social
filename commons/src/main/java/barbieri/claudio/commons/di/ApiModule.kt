package barbieri.claudio.commons.di

import android.util.Base64
import barbieri.claudio.commons.data.api.SocialIfesApi
import barbieri.claudio.commons.data.api.SocialIfesApiMock
import barbieri.claudio.commons.data.repository.CustomException
import barbieri.claudio.commons.data.repository.SocialIfesRepositoryImpl
import barbieri.claudio.commons.domain.model.UserAuth
import barbieri.claudio.commons.domain.repository.SocialIfesRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApiService(user: UserAuth?): SocialIfesApi {
        return if (false) {
            Retrofit.Builder()
                .baseUrl("")
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(
                            object : Interceptor {
                                override fun intercept(chain: Interceptor.Chain): Response {
                                    val originalRequest = chain.request()
                                    val response = if (user != null) {
                                        val newRequest = originalRequest.newBuilder()
                                            .header("Authorization", getAuth(user))
                                            .build()
                                        chain.proceed(newRequest)
                                    } else chain.proceed(chain.request())
                                    handleResponse(response)
                                    return response
                                }
                            }
                        ).build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SocialIfesApi::class.java)
        } else SocialIfesApiMock()
    }

    private fun handleResponse(response: Response) {
        try {
            val jsonResponse = Gson().fromJson(response.body.toString(), JsonObject::class.java)
            val code = jsonResponse.get(SUCCESS_KEY).asInt
            val message = jsonResponse.get(ERROR_KEY).asString
            if (code != SUCCESS_CODE) throw CustomException(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getAuth(user: UserAuth): String {
        val auth = "${user.login}:${user.password}"
        val encodedAuth = Base64.encode(auth.toByteArray(), Base64.NO_WRAP)
        val authHeaderValue = "Basic $encodedAuth"
        return authHeaderValue
    }

    @Provides
    @Singleton
    fun providesRepository(api: SocialIfesApi): SocialIfesRepository =
        SocialIfesRepositoryImpl(api)

    private const val SUCCESS_CODE = 1
    private const val SUCCESS_KEY = "sucesso"
    private const val ERROR_KEY = "erro"
}