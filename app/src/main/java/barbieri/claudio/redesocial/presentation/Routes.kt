package barbieri.claudio.redesocial.presentation

sealed class Routes(val route : String) {
    object Home : Routes("home")
    object Login : Routes("login")
    object Signup : Routes("signup")
}