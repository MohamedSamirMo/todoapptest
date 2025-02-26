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

    suspend fun getTodos(): List<Todo> = withContext(Dispatchers.IO) {
        return@withContext todoDao.getAllTodos().toTodoList()
    }

    suspend fun fetchAndStoreTodos(): List<Todo> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTodos()
                if (response.isSuccessful) {
                    response.body()?.let { todos ->
                        val entities = todos.map { it.toEntity() }
                        todoDao.insertTodos(entities) // تخزين البيانات الجديدة في Room
                        return@withContext getTodos()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return@withContext getTodos() // في حالة الفشل، نرجع البيانات من Room
        }
    }

    suspend fun getTodoById(id: Int): Todo? = withContext(Dispatchers.IO) {
        return@withContext todoDao.getTodoById(id)?.toTodo()
    }

    suspend fun searchTodos(query: String): List<Todo> = withContext(Dispatchers.IO) {
        return@withContext todoDao.searchTodos(query).toTodoList()
    }
}
