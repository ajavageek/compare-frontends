package ch.frankel.blog.frontends

import com.github.mvysny.karibudsl.v10.columnFor
import com.github.mvysny.karibudsl.v10.grid
import com.github.mvysny.karibudsl.v10.h1
import com.github.mvysny.kaributools.getCell
import com.github.mvysny.kaributools.refresh
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.NativeLabel
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route("/")
@PageTitle("Vaadin")
class TodoView(todos: ArrayList<Todo>) : VerticalLayout() {

    private val checkboxRenderer = ComponentRenderer { todo: Todo ->
        Checkbox(todo.completed).apply {
            addValueChangeListener { todo.completed = it.value }
        }
    }

    init {
        h1("Vaadin")
        grid<Todo> {
            setItems(todos)
            addThemeVariants(GridVariant.LUMO_ROW_STRIPES)
            val labelProp = Todo::label
            val completedProp = Todo::completed
            columnFor(Todo::id) { setHeader("ID") }
            columnFor(labelProp)
            columnFor(completedProp) {
                renderer = checkboxRenderer
            }
            appendFooterRow().apply {
                getCell(completedProp).component = Button("Clean up") {
                    todos.removeIf { it.completed }
                    refresh()
                }
            }
            appendFooterRow().apply {
                val labelField = TextField("New Task").apply {
                    width = "100%"
                }
                getCell(labelProp).component = labelField
                getCell(completedProp).component = Button("Add") {
                    todos.add(labelField.value)
                    labelField.clear()
                    refresh()
                }
            }
        }
    }
}
