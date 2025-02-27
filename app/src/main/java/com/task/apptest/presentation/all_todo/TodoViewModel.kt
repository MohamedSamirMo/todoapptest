package com.task.apptest.presentation.all_todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.apptest.data.repository.TodoRepository
import com.task.apptest.domain.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos = _todos.asStateFlow()

    private val _todoDetails = MutableStateFlow<Todo?>(null)
    val todoDetails = _todoDetails.asStateFlow()

    private var page = 1
    private val pageSize = 2

    fun fetchTodos(hasInternet: Boolean) {
        viewModelScope.launch {
            if (hasInternet) {
                repository.fetchAndStoreTodos(page, pageSize)
            }
            val newTodos = repository.getTodos(page, pageSize)
            _todos.value += newTodos
            page++
        }
    }

    fun fetchTodoById(id: Int) {
        viewModelScope.launch {
            _todoDetails.value = repository.getTodoById(id)
        }
    }
}
