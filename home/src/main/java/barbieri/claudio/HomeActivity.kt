package barbieri.claudio

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
import androidx.navigation.toRoute
import barbieri.claudio.commons.theme.RedeSocialTheme
import barbieri.claudio.createpost.CreatePostScreen
import barbieri.claudio.home.HomeScreen
import barbieri.claudio.post.PostScreen
import barbieri.claudio.profile.ProfileScreen
import barbieri.claudio.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedeSocialTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("Rede Social", color = Color.Companion.White) },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color.Companion.Blue
                            ),
                            navigationIcon = {
                                Icon(
                                    modifier = Modifier.clickable {
                                        this@HomeActivity.onBackPressed()
                                    },
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                        )
                    },
                    modifier = Modifier.Companion.fillMaxSize(),
                ) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Routes.Home,
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable<Routes.Home> {
                            HomeScreen(
                                navigateToPost = { postId ->
                                    navController.navigate(Routes.Post(postId))
                                },
                                navigateToCreatePost = {
                                    navController.navigate(Routes.CreatePost)
                                },
                                navigateToSearch = {
                                    navController.navigate(Routes.Search)
                                }
                            )
                        }
                        composable<Routes.Post> {
                            PostScreen(
                                postId = (it.toRoute() as Routes.Post).postId,
                                navigateToProfile = { login ->
                                    navController.navigate(Routes.Profile(login))
                                }
                            )
                        }
                        composable<Routes.CreatePost> {
                            CreatePostScreen(
                                goBack = { this@HomeActivity.onBackPressedDispatcher.onBackPressed() }
                            )
                        }
                        composable<Routes.Search> {
                            SearchScreen(
                                navigateToProfile = { login ->
                                    navController.navigate(
                                        Routes.Profile(
                                            login
                                        )
                                    )
                                }
                            )
                        }
                        composable<Routes.Profile> {
                            ProfileScreen(
                                navigateToPost = { postId ->
                                    navController.navigate(Routes.Post(postId))
                                },
                                login = (it.toRoute() as Routes.Profile).login,
                            )
                        }
                    }
                }
            }
        }
    }
}