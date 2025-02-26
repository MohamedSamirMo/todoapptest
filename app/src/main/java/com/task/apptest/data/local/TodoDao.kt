package com.task.apptest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAllTodos(): List<TodoEntity>

    @Query("SELECT * FROM todos WHERE id = :id")
    fun getTodoById(id: Int): TodoEntity?

    @Query("SELECT * FROM todos LIMIT :limit")
    fun getLimitedTodos(limit: Int): List<TodoEntity>

    @Query("SELECT * FROM todos WHERE title LIKE '%' || :query || '%'")
    fun searchTodos(query: String): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertTodos(todos: List<TodoEntity>)

    @Query("DELETE FROM todos")
     fun clearTodos()
}
