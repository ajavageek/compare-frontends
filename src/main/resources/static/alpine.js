document.addEventListener('alpine:init', () => {
    Alpine.data('app', () => ({
        title: window.alpineData.title,
        todos: window.alpineData.todos,
        label: '',
        check(id) {
            axios.patch(`/api/todo/${id}`, {checked: event.target.checked})
        },
        cleanup() {
            axios.delete('/api/todo:cleanup').then(response => {
                this.todos = response.data
            })
        },
        create() {
            axios.post('/api/todo', {label: this.label}).then(response => {
                this.todos.push(response.data)
            }).then(() => {
                this.label = ''
            })
        }
    }))
})
