package org.setu.focussphere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import org.setu.focussphere.databinding.ActivityTaskBinding
import org.setu.focussphere.models.PriorityLevel
import org.setu.focussphere.models.TaskModel
import org.setu.focussphere.models.TaskStatus
import timber.log.Timber
import timber.log.Timber.Forest.i

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    var task = TaskModel()
    val tasks = ArrayList<TaskModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("task activity started")

        val priorityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, PriorityLevel.values())
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = priorityAdapter

        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, TaskStatus.values())
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.statusSpinner.adapter = statusAdapter

        binding.addTaskButton.setOnClickListener() {
            i("Button onClick triggered")
            val selectedPriority = binding.prioritySpinner.selectedItem as PriorityLevel
            val selectedStatus = binding.statusSpinner.selectedItem as TaskStatus

            task.title = binding.taskTitle.text.toString()
            task.description = binding.taskDescription.text.toString()
            task.priorityLevel = selectedPriority
            task.status = selectedStatus

            if (task.title.isNotBlank() && !tasks.contains(task)) {
                tasks.add(task.copy())
                binding.taskTitle.text.clear()
                binding.taskDescription.text.clear()
                i("Button Pressed: $task")
                Snackbar.make(it,(	"\ud83e\udd70") + "  " + getString(R.string.task_add_text), Snackbar.LENGTH_SHORT).show()
                for (i in tasks.indices)
                    { i("Task[$i]:${this.tasks[i]}") }

            } else if (task.title.isBlank()){
                Snackbar.make(it, "\ud83d\ude31" + "  Task must contain a title", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(it, "\ud83d\ude31" + "  duplicate task not added", Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}