package barbieri.claudio.commons.domain.model

import java.io.Serializable

data class User(
    val login: String,
    val name: String,
    val city: String,
    val birthDate: String,
    val image: String,
    val following: Boolean
) : Serializable{
    companion object {
        fun mock() = User(
            login = "login",
            name = "nome",
            city = "cidade",
            birthDate = "1997-10-19",
            image = "https://s2-techtudo.glbimg.com/4uvIHOMReah_7syBff5VD9rnkV4=/0x0:1184x896/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2024/a/k/6T2EyXSNmezAkYcsNh9g/leonardoai.jpg",
            following = true
        )
    }
}
