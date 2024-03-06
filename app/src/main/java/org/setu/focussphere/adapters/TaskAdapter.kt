//package org.setu.focussphere.adapters
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import org.setu.focussphere.data.entities.Task
//import org.setu.focussphere.databinding.CardTaskBinding
//
//interface TaskListener {
//    fun onTaskClick(task: Task, position: Int)
//}
//
//class TaskAdapter constructor(private var tasks: List<Task>,
//                              private val listener: TaskListener) :
//    RecyclerView.Adapter<TaskAdapter.MainHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
//        val binding = CardTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//
//        return MainHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MainHolder, position: Int) {
//        val task = tasks[holder.adapterPosition]
//        holder.bind(task, listener)
//    }
//
//    override fun getItemCount(): Int = tasks.size
//
//    fun updateTasks(newTasks: List<Task>) {
//        tasks = newTasks
//        notifyDataSetChanged()
//    }
//
//    class MainHolder(private val binding : CardTaskBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(task: Task, listener: TaskListener) {
//            binding.taskTitle.text = task.title
//            binding.description.text = task.description
//            binding.priority.text = task.priorityLevel.toString()
//            binding.status.text = task.status.toString()
//            binding.root.setOnClickListener { listener.onTaskClick(task, adapterPosition)}
//        }
//    }
//}

