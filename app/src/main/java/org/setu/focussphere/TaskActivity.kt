package org.setu.focussphere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import org.setu.focussphere.databinding.ActivityTaskBinding
import org.setu.focussphere.models.PriorityLevel
import org.setu.focussphere.models.TaskStatus
import timber.log.Timber
import timber.log.Timber.Forest.i

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding

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

        binding.addTaskButton.setOnClickListener {
            val taskTitle = binding.taskTitle.text.toString()
            val taskDescription = binding.taskDescription.text.toString()

            val selectedPriority = binding.prioritySpinner.selectedItem as PriorityLevel
            val selectedStatus = binding.statusSpinner.selectedItem as TaskStatus

            val taskAddedText = getString(R.string.task_add_text)
            Snackbar.make(it, taskAddedText, Snackbar.LENGTH_SHORT).show()
//            Toast.makeText(this, taskAddedText, Toast.LENGTH_SHORT).show()
        }
    }
}