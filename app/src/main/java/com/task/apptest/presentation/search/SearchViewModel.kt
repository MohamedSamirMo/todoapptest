package com.task.apptest.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.apptest.domain.model.Todo
import com.task.apptest.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Todo>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    fun searchTodos(query: String) {
        viewModelScope.launch {
            _searchResults.value = if (query.isBlank()) {
                emptyList()
            } else {
                val results = repository.searchTodos(query)
                results
            }
        }
    }
}
