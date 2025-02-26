package com.task.apptest.common.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.apptest.data.local.TodoDao
import com.task.apptest.data.local.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object{
        const val DATABASE_NAME = "todo_database"
    }
}
