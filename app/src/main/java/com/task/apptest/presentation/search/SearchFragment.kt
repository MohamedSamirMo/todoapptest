package com.task.apptest.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.apptest.R
import com.task.apptest.common.utils.enableEdgeToEdge
import com.task.apptest.databinding.FragmentSearchBinding
import com.task.apptest.presentation.all_todo.TodoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var todoAdapter: TodoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        enableEdgeToEdge()
        setupRecyclerView()
        setupSearch()
        observeSearchResults()
        bindAppBar()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter()
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchTodos(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchTodos(it) }
                return true
            }
        })
    }

    private fun observeSearchResults() {
        lifecycleScope.launch {
            viewModel.searchResults.collect { todos ->
                todoAdapter.updateTodos(todos)
            }
        }
    }

    private fun bindAppBar() {
        binding.appBar.tvScreenTitle.text = getString(R.string.search)
        binding.appBar.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        binding.appBar.btnSearch.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
