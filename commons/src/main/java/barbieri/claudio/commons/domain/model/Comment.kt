package barbieri.claudio.commons.domain.model

import java.util.Date

data class Comment(
    val id: Int,
    val text: String,
    val date: Date,
    val login: String,
    val name: String,
    val userPhoto: String,
) {
    companion object {
        fun mock() = Comment(
            id = 1,
            text = "comentario",
            date = Date(),
            login = "login",
            name = "nome",
            userPhoto = "https://s2-techtudo.glbimg.com/4uvIHOMReah_7syBff5VD9rnkV4=/0x0:1184x896/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2024/a/k/6T2EyXSNmezAkYcsNh9g/leonardoai.jpg"
        )
    }
}