package barbieri.claudio.redesocial.presentation

import barbieri.claudio.HomeActivity
import barbieri.claudio.commons.navigation.NavigateHandler
import barbieri.claudio.commons.navigation.NavigateHandlerImpl
import barbieri.claudio.commons.navigation.NavigationKeys
import barbieri.claudio.login.presentation.LoginActivity
import barbieri.claudio.signup.SignupActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
@InstallIn(SingletonComponent::class)
object FeaturesModule {

    @Provides
    @IntoMap
    @StringKey(NavigationKeys.LOGIN_MODULE)
    fun provideLoginNavigateHandler(): NavigateHandler =
        object : NavigateHandler by NavigateHandlerImpl(LoginActivity::class.java) {}

    @Provides
    @IntoMap
    @StringKey(NavigationKeys.HOME_MODULE)
    fun provideHomeNavigateHandler(): NavigateHandler =
        object : NavigateHandler by NavigateHandlerImpl(HomeActivity::class.java) {}

    @Provides
    @IntoMap
    @StringKey(NavigationKeys.SIGNUP_MODULE)
    fun provideSignupNavigateHandler(): NavigateHandler =
        object : NavigateHandler by NavigateHandlerImpl(SignupActivity::class.java) {}
}