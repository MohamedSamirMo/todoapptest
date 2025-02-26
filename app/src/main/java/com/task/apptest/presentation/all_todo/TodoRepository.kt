package com.task.apptest.presentation.all_todo

import com.task.apptest.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepository @Inject constructor(private val apiService: ApiService) {

//    suspend fun getTodos() = withContext(Dispatchers.IO) {
//        apiService.getTodos()
//    }

    suspend fun getTodoById(id: Int) = withContext(Dispatchers.IO) {
        apiService.getTodoById(id)
    }

    suspend fun getLimitedTodos(limit: Int) = withContext(Dispatchers.IO) {
        apiService.getLimitedTodos(limit)
    }

    suspend fun searchTodos(query: String) = withContext(Dispatchers.IO) {
        apiService.searchTodos(query)
    }
}
