package com.task.apptest.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.task.apptest.domain.model.Todo

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val completed: Boolean,
    val description: String,
    val dueDate: String,
    val priority: String
){
    fun toTodo() = Todo(
        id = id,
        title = title,
        completed = completed,
        description = description,
        dueDate = dueDate,
        priority = priority
    )
}
fun List<TodoEntity>.toTodoList(): List<Todo> = map { it.toTodo() }
fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = this.id,
        title = this.title,
        completed = this.completed,
        description = this.description,
        dueDate = this.dueDate,
        priority = this.priority
    )
}