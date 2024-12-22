package barbieri.claudio.commons.preferences

import android.content.Context
import barbieri.claudio.commons.domain.model.UserAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUser(user: UserAuth) {
        with(sharedPreferences.edit()) {
            putString("login", user.login)
            putString("password", user.password)
            apply()
        }
    }

    fun getUser(): UserAuth? {
        val login = sharedPreferences.getString("login", null)
        val password = sharedPreferences.getString("password", null)
        return if (login != null && password != null) {
            UserAuth(login, password)
        } else {
            null
        }
    }
}