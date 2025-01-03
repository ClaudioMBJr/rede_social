package barbieri.claudio.redesocial.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import barbieri.claudio.HomeActivity
import barbieri.claudio.commons.domain.model.UserAuth
import barbieri.claudio.commons.preferences.SharedPreferencesManager
import barbieri.claudio.login.presentation.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (sharedPreferencesManager.getUser() != null)
            startActivity(Intent(this, HomeActivity::class.java))
        else
            startActivity(Intent(this, LoginActivity::class.java))
    }
}