package barbieri.claudio.redesocial.inject

import android.content.Context
import barbieri.claudio.redesocial.domain.model.UserAuth
import barbieri.claudio.redesocial.preferences.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(@ApplicationContext context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideUser(
        sharedPreferencesManager: SharedPreferencesManager
    ): UserAuth? = sharedPreferencesManager.getUser()

}