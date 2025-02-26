package com.task.apptest.presentation.all_todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.apptest.databinding.ItemTodoBinding
import com.task.apptest.domain.model.Todo

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    })

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun updateTodos(newTodos: List<Todo>) {
        differ.submitList(newTodos)
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class TodoViewHolder(
        private val binding: ItemTodoBinding,
        private val onItemClickListener: ((Int) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.tvTitle.text = todo.title
            binding.tvDescription.text = todo.description

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(todo.id)
            }
        }
    }
}
