package com.task.apptest.domain.model


data class Todo(
    val id: Int,
    val completed: Boolean,
    val description: String,
    val dueDate: String,
    val priority: String,
    val title: String
)