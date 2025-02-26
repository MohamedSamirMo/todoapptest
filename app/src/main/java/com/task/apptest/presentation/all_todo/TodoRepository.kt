package com.task.apptest.presentation.all_todo

import com.task.apptest.data.local.TodoDao
import com.task.apptest.data.local.toEntity
import com.task.apptest.data.local.toTodoList
import com.task.apptest.data.remote.ApiService
import com.task.apptest.domain.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val apiService: ApiService,
    private val todoDao: TodoDao
) {

    private suspend fun getTodos(): List<Todo> = withContext(Dispatchers.IO) {
        val todos = todoDao.getAllTodos().toTodoList()
        todos
    }

    suspend fun fetchAndStoreTodos(): List<Todo> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getTodos()
            if (response.isSuccessful) {
                response.body()?.let { todos ->
                    val entities = todos.map { it.toEntity() }
                    todoDao.insertTodos(entities)
                    return@withContext getTodos()
                }
            }
            return@withContext emptyList()
        }
    }

    suspend fun getTodoById(id: Int): Todo? = withContext(Dispatchers.IO) {
        val todo = todoDao.getTodoById(id)?.toTodo()
        todo
    }
    suspend fun searchTodos(query: String): List<Todo> = withContext(Dispatchers.IO) {
        val results = todoDao.searchTodos(query).toTodoList()
        results
    }
}
