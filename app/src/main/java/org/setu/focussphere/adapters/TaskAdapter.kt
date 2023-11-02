package org.setu.focussphere.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.setu.focussphere.databinding.CardTaskBinding
import org.setu.focussphere.models.TaskModel

class TaskAdapter constructor(private var tasks: List<TaskModel>) :
    RecyclerView.Adapter<TaskAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val task = tasks[holder.adapterPosition]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    class MainHolder(private val binding : CardTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskModel) {
            binding.taskTitle.text = task.title
            binding.description.text = task.description
            binding.priority.text = task.priorityLevel.toString()
            binding.status.text = task.status.toString()
        }
    }
}