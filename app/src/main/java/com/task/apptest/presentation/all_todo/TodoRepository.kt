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
    suspend fun getTodos(page: Int, pageSize: Int): List<Todo> = withContext(Dispatchers.IO) {
        val startIndex = (page - 1) * pageSize
        todoDao.getAllTodos()
            .toTodoList()
            .drop(startIndex)
            .take(pageSize)
    }
    suspend fun fetchAndStoreTodos(page: Int, pageSize: Int): List<Todo> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTodos()
                if (response.isSuccessful) {
                    response.body()?.let { todos ->
                        val entities = todos.map { it.toEntity() }
                        todoDao.insertTodos(entities)
                        return@withContext getTodos(page, pageSize)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return@withContext getTodos(page, pageSize)
        }
    }

    suspend fun getTodoById(id: Int): Todo? = withContext(Dispatchers.IO) {
        return@withContext todoDao.getTodoById(id)?.toTodo()
    }

    suspend fun searchTodos(query: String): List<Todo> = withContext(Dispatchers.IO) {
        return@withContext todoDao.searchTodos(query).toTodoList()
    }
}
