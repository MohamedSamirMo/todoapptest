package com.task.apptest.presentation.all_todo

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.apptest.R
import com.task.apptest.common.utils.enableEdgeToEdge
import com.task.apptest.databinding.FragmentTodoBinding
import com.task.apptest.presentation.description.TodoDetailsFragment.Companion.TODO_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoFragment : Fragment(R.layout.fragment_todo) {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by viewModels()
    private lateinit var todoAdapter: TodoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTodoBinding.bind(view)
        enableEdgeToEdge()
        setupRecyclerView()
        observeTodos()
        initUI()
        bindAppBar()
        observeInternetConnection()
    }

    private fun initUI() {
        val isConnected = isInternetAvailable(requireContext())

        if (isConnected) {
            binding.shimmerViewContainer.startShimmer()
            binding.tvNoInternetConnection.visibility = View.GONE
        } else {
            binding.rvTodos.visibility = View.GONE
            binding.shimmerViewContainer.visibility = View.GONE
            binding.tvNoInternetConnection.visibility = View.VISIBLE
        }

        viewModel.fetchTodos(isConnected) // إرسال حالة الإنترنت للـ ViewModel
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTodos.apply {
            this.layoutManager = layoutManager
            adapter = todoAdapter
        }
        todoAdapter.setOnItemClickListener { todo ->
            val bundle = Bundle().apply {
                putInt(TODO_ID, todo)
            }
            findNavController().navigate(R.id.action_todoFragment_to_todoDetailsFragment, bundle)
        }
    }

    private fun observeTodos() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todos.collect { todos ->
                if (todos.isNotEmpty()) {
                    binding.tvNoInternetConnection.visibility = View.GONE
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.rvTodos.visibility = View.VISIBLE
                } else if (!isInternetAvailable(requireContext())) {
                    binding.tvNoInternetConnection.visibility = View.VISIBLE
                }
                todoAdapter.updateTodos(todos)
            }
        }
    }

    private fun bindAppBar() {
        binding.appBar.tvScreenTitle.text = getString(R.string.todos)
        binding.appBar.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        binding.appBar.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_todoFragment_to_searchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun observeInternetConnection() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                requireActivity().runOnUiThread {
                    binding.tvNoInternetConnection.visibility = View.GONE
                    binding.rvTodos.visibility = View.VISIBLE
                    binding.shimmerViewContainer.startShimmer()
                    viewModel.fetchTodos(true) // تحميل البيانات الجديدة عند عودة الإنترنت
                }
            }

            override fun onLost(network: Network) {
                requireActivity().runOnUiThread {
                    binding.tvNoInternetConnection.visibility = View.VISIBLE
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                }
            }
        })
    }
}
