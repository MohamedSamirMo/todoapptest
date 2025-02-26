package com.task.apptest.presentation.all_todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun fetchTodos(hasInternet: Boolean) {
        viewModelScope.launch {
            val cachedTodos = repository.getTodos()
            _todos.value = cachedTodos
            if (hasInternet) {
                val newTodos = repository.fetchAndStoreTodos()
                _todos.value = newTodos
            }
        }
    }

    fun fetchTodoById(id: Int) {
        viewModelScope.launch {
            _todoDetails.value = repository.getTodoById(id)
        }
    }
}
