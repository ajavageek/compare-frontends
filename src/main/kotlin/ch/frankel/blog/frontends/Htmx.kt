package ch.frankel.blog.frontends

import org.springframework.web.servlet.function.router

fun htmx(todos: MutableList<Todo>) = router {
    "/htmx".nest {
        GET("") {
            ok().render("htmx", mapOf("title" to "HTMX", "todos" to todos))
        }
        PATCH("/todo/{id}") { req ->
            val id = req.pathVariable("id").toInt()
            val todo = todos.single { it.id == id }
            req.param("checked").ifPresent {
                todo.completed = it.toBoolean()
            }
            noContent().build()
        }
        DELETE("/todo:cleanup") {
            todos.removeIf { it.completed }
            ok().render("htmx/lines", mapOf("todos" to todos))
        }
        POST("/todo") { req ->
            req.param("label").ifPresent { todos.add(it) }
            ok().render("htmx/table", mapOf("todos" to todos))
        }
    }
}
