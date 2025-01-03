package barbieri.claudio.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import barbieri.claudio.commons.navigation.NavigateHandler
import barbieri.claudio.commons.navigation.NavigationKeys
import barbieri.claudio.commons.theme.RedeSocialTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class SignupActivity : ComponentActivity() {

    @Inject
    lateinit var navigate: Map<String, @JvmSuppressWildcards NavigateHandler>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedeSocialTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("Rede Social", color = Color.White) },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color.Blue
                            ),
                            navigationIcon = {
                                Icon(
                                    modifier = Modifier.clickable {
                                        this@SignupActivity.onBackPressed()
                                    },
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Routes.Signup.route,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable(Routes.Signup.route) {
                            SignupScreen {
                                navigate[NavigationKeys.LOGIN_MODULE]?.launchFeature(this@SignupActivity)
                            }
                        }
                    }
                }
            }
        }
    }

    sealed class Routes(val route: String) {
        object Signup : Routes("home")
    }
}