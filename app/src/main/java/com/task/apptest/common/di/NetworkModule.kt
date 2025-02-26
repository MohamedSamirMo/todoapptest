package com.task.apptest.common.di
import com.task.apptest.data.remote.ApiService
import com.task.apptest.data.remote.ssl.getUnsafeOkHttpClient
import com.task.apptest.common.utils.Api
import com.task.apptest.data.local.TodoDao
import com.task.apptest.data.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .client(getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideTodoRepository(apiService: ApiService, todoDao: TodoDao): TodoRepository {
        return TodoRepository(apiService, todoDao)
    }
}
