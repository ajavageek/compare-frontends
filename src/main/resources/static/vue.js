document.addEventListener('DOMContentLoaded', () => {
    const { createApp, ref, h } = Vue

    const TodoLine = {
        props: ['todo'],
        template: document.getElementById('todo-line').innerHTML,
        setup(props) {
            const check = function (event) {
                const { todo } = props
                axios.patch(`/api/todo/${todo.id}`, { checked: event.target.checked })
            }
            return { check }
        }
    }

    const TodosApp = {
        props: ['title', 'todos'],
        components: { TodoLine },
        template: document.getElementById('todos-app').innerHTML,
        setup() {
            const label = ref('')
            const create = function() {
                axios.post('/api/todo', { label: label.value }).then(response => {
                    state.value.todos.push(response.data)
                }).then(() => {
                    label.value = ''
                })
            }
            const cleanup = function() {
                axios.delete('/api/todo:cleanup').then(response => {
                    state.value.todos = response.data
                })
            }
            return { label, create, cleanup }
        }
    }

    const state = ref({
        title: window.vueData.title,
        todos: window.vueData.todos,
    })

    createApp({
        components: { TodosApp },
        setup() {
            return { ...state.value }
        },
        render() {
            return h(TodosApp, {
                todos: state.value.todos,
                title: state.value.title
            })
        }
    }).mount('#app');

    document.title = window.vueData.title
});
