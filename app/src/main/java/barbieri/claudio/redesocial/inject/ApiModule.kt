package barbieri.claudio.redesocial.inject

import android.util.Base64
import barbieri.claudio.redesocial.data.api.SocialIfesApi
import barbieri.claudio.redesocial.data.api.SocialIfesApiMock
import barbieri.claudio.redesocial.domain.model.UserAuth
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
        return if (true) {
            Retrofit.Builder()
                .baseUrl("")
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(
                            object : Interceptor {
                                override fun intercept(chain: Interceptor.Chain): Response {
                                    val originalRequest = chain.request()
                                    return if (user != null) {
                                        val newRequest = originalRequest.newBuilder()
                                            .header("Authorization", getAuth(user))
                                            .build()
                                        chain.proceed(newRequest)
                                    } else chain.proceed(chain.request())
                                }
                            }
                        ).build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SocialIfesApi::class.java)
        } else {
            SocialIfesApiMock()
        }
    }

    private fun getAuth(user: UserAuth): String {
        val auth = "${user.login}:${user.password}"
        val encodedAuth = Base64.encode(auth.toByteArray(), Base64.NO_WRAP)
        val authHeaderValue = "Basic $encodedAuth"
        return authHeaderValue
    }
}