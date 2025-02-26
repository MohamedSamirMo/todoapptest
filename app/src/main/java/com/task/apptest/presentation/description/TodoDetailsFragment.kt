package com.task.apptest.presentation.description

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.task.apptest.R
import com.task.apptest.common.utils.enableEdgeToEdge
import com.task.apptest.databinding.FragmentTodoDetailsBinding
import com.task.apptest.presentation.all_todo.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoDetailsFragment : Fragment(R.layout.fragment_todo_details) {

    private var _binding: FragmentTodoDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTodoDetailsBinding.bind(view)
        enableEdgeToEdge()
        initNavigation()
        observeTodoDetails()
        bindAppBar()
    }

    private fun observeTodoDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.shimmerViewContainer.startShimmer()
            binding.shimmerViewContainer.visibility = View.VISIBLE

            viewModel.todoDetails.collect { todo ->
                todo?.let {
                    binding.tvTitle.text = it.title
                    binding.tvDescription.text = it.description
                    binding.tvDueDate.text = it.dueDate
                    binding.tvPriority.text = it.priority
                    binding.cbCompleted.isChecked = it.completed

                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                }
            }
        }
    }

    private fun initNavigation() {
        val todoId = arguments?.getInt(TODO_ID) ?: return
        viewModel.fetchTodoById(todoId)
    }

    private fun bindAppBar() {
        binding.appBar.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        binding.appBar.tvScreenTitle.text = getString(R.string.todo_details)
        binding.appBar.btnSearch.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TODO_ID = "todo_id"
    }
}
