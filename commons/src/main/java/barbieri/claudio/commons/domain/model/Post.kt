package barbieri.claudio.commons.domain.model

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
            userPhoto = "https://s2-techtudo.glbimg.com/L9wb1xt7tjjL-Ocvos-Ju0tVmfc=/0x0:1200x800/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2023/q/l/TIdfl2SA6J16XZAy56Mw/canvaai.png",
            id = 1,
            image = "https://s2-techtudo.glbimg.com/L9wb1xt7tjjL-Ocvos-Ju0tVmfc=/0x0:1200x800/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2023/q/l/TIdfl2SA6J16XZAy56Mw/canvaai.png"
        )
    }
}
