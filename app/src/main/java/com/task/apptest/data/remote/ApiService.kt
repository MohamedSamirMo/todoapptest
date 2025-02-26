package com.task.apptest.data.remote

import com.task.apptest.domain.model.Todo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("todos")
    suspend fun getTodos(): Response<List<Todo>>

    @GET("todos/{id}")
    suspend fun getTodoById(@Path("id") id: Int): Response<Todo>

    @GET("todos")
    suspend fun getLimitedTodos(@Query("limit") limit: Int): Response<List<Todo>>

    @GET("todos")
    suspend fun searchTodos(@Query("search") query: String): Response<List<Todo>>
}
