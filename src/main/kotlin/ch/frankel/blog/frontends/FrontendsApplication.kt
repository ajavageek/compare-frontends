package ch.frankel.blog.frontends

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans

@SpringBootApplication
class FrontendsApplication

data class Todo(val id: Int, var label: String, var completed: Boolean = false)

internal fun MutableList<Todo>.add(label: String): Todo {
    val id = maxOfOrNull { it.id }?.plus(1) ?: 1
    return Todo(id, label).also { add(it) }
}

fun config() = beans {
    bean {
        mutableListOf(
            Todo(1, "Go to the groceries"),
            Todo(2, "Walk the dog"),
            Todo(3, "Take out the trash")
        )
    }
    bean { home() }
    bean { api(ref()) }
    bean { vue(ref()) }
    bean { htmx(ref()) }
    bean { alpine(ref()) }
}

fun main(args: Array<String>) {
    runApplication<FrontendsApplication>(*args) {
        addInitializers(config())
    }
}
