package org.setu.focussphere.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.setu.focussphere.R
import org.setu.focussphere.databinding.ActivityTaskBinding
import org.setu.focussphere.main.MainApp
import org.setu.focussphere.models.PriorityLevel
import org.setu.focussphere.models.TaskModel
import org.setu.focussphere.models.TaskStatus
import timber.log.Timber.Forest.i

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    var task = TaskModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAddTask.title = title
        setSupportActionBar(binding.toolbarAddTask)

        app = application as MainApp
        i("Task Activity Started")

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

            if (task.title.isNotBlank() && !app.tasks.contains(task)) {
                app.tasks.add(task.copy())
                binding.taskTitle.text.clear()
                binding.taskDescription.text.clear()
                i("Button Pressed: $task")
                Snackbar.make(it,(	"\ud83e\udd70") + "  " + getString(R.string.task_add_text), Snackbar.LENGTH_SHORT).show()
                for (i in app.tasks.indices) {
                    i("Task[$i]:${this.app.tasks[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else if (task.title.isBlank()){
                Snackbar.make(it, "\ud83d\ude31" + "  Task must contain a title", Snackbar.LENGTH_LONG).show()
            }
            else {
                Snackbar.make(it, "\ud83d\ude31" + "  duplicate task not added", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_task, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}