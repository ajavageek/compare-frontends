package ch.frankel.blog.frontends

import org.springframework.web.servlet.function.router

fun vue(todos: List<Todo>) = router {
    GET("/vue") {
        ok().render("vue", mapOf("title" to "Vue.js", "todos" to todos))
    }
}
