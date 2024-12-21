package barbieri.claudio.redesocial.domain.model

import java.util.Date

data class Post(
    val date: Date,
    val text: String,
    val login: String,
    val name: String,
    val userPhoto: String,
    val id: Int,
    val image: String
) {
    companion object {
        fun mock() = Post(
            date = Date(),
            text = "texto",
            login = "login",
            name = "nome",
            userPhoto = "https://tinyurl.com/6w7rfev3",
            id = 1,
            image = "https://tinyurl.com/6w7rfev3"
        )
    }
}
