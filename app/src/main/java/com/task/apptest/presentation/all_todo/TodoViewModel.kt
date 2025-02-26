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

    private var currentPage = 0
    private val pageSize = 5
    private var isLoading = false
    private var hasMoreData = true

    private val _todoDetails = MutableStateFlow<Todo?>(null)
    val todoDetails = _todoDetails.asStateFlow()

    fun fetchTodos() {
        if (isLoading || !hasMoreData) return
        isLoading = true

        viewModelScope.launch {
            val response = repository.getLimitedTodos(pageSize * (currentPage + 1))
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.size <= _todos.value.size) {
                        hasMoreData = false
                    } else {
                        _todos.emit(it)
                        currentPage++
                    }
                }
            }
            isLoading = false
        }
    }

    fun fetchTodoById(id: Int) {
        viewModelScope.launch {
            val response = repository.getTodoById(id)
            if (response.isSuccessful) {
                response.body()?.let { _todoDetails.emit(it) }
            }
        }
    }
}
