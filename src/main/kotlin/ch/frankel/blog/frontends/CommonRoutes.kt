package ch.frankel.blog.frontends

import org.springframework.web.servlet.function.router

fun api(todos: MutableList<Todo>) = router {
    "/api".nest {
        PATCH("/todo/{id}") { req ->
            val id = req.pathVariable("id").toInt()
            val todo = todos.single { it.id == id }

            data class UpdateTodo(val checked: Boolean)
            req.body(UpdateTodo::class.java).let { todo.completed = it.checked }
            ok().body(todo)
        }

        DELETE("/todo:cleanup") {
            todos.removeIf { it.completed }
            ok().body(todos)
        }
        POST("/todo") { req ->
            data class NewTodo(val label: String)

            val newTodo = req.body(NewTodo::class.java)
            val todo = todos.add(newTodo.label)
            created(req.uriBuilder().pathSegment(todo.id.toString()).build()).body(todo)
        }
    }
}

fun home() = router {
    GET("/") { ok().render("index") }
}
