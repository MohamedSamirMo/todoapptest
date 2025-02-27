package com.task.apptest.data.remote

import com.task.apptest.domain.model.Todo
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("todos")
    suspend fun getTodos(): Response<List<Todo>>
}
