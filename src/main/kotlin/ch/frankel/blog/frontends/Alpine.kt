package ch.frankel.blog.frontends

import org.springframework.web.servlet.function.router

fun alpine(todos: MutableList<Todo>) = router {
    "/alpine".nest {
        GET("") {
            ok().render("alpine", mapOf("title" to "Alpine.js", "todos" to todos))
        }
    }
}
