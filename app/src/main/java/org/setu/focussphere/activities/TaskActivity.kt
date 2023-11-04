package org.setu.focussphere.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var task = TaskModel()
    lateinit var app : MainApp

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { i("Map Loaded") }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAddTask.title = title
        setSupportActionBar(binding.toolbarAddTask)

        app = application as MainApp
        i("Task Activity Started")

        registerMapCallback()

        var edit = false

        val priorityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, PriorityLevel.values())
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = priorityAdapter

        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, TaskStatus.values())
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.statusSpinner.adapter = statusAdapter

        if (intent.hasExtra("task_edit")) {
            edit = true
            task = intent.extras?.getParcelable("task_edit")!!
            binding.taskTitle.setText(task.title)
            binding.taskDescription.setText(task.description)
            binding.statusSpinner.setSelection(task.status.ordinal)
            binding.prioritySpinner.setSelection(task.priorityLevel.ordinal)
            binding.addTaskButton.setText(R.string.save_task)
        }

        binding.addTaskButton.setOnClickListener() { it ->
            i("Button onClick triggered")
            val selectedPriority = binding.prioritySpinner.selectedItem as PriorityLevel
            val selectedStatus = binding.statusSpinner.selectedItem as TaskStatus

            task.title = binding.taskTitle.text.toString()
            task.description = binding.taskDescription.text.toString()
            task.priorityLevel = selectedPriority
            task.status = selectedStatus

            if (task.title.isBlank()){
                Snackbar.make(it, R.string.snackbar_warning_missingTitle, Snackbar.LENGTH_LONG).show()
            }
            else if (!edit && app.tasks.findAll().any { it.title == task.title && it.description == task.description }) {
                Snackbar.make(it, R.string.snackbar_warning_duplicateTask, Snackbar.LENGTH_SHORT).show()
            }
            else {
                if (edit) {
                    app.tasks.update(task.copy())
                } else {
                    app.tasks.create(task.copy())
                    binding.taskTitle.text.clear()
                    binding.taskDescription.text.clear()
                    i("Button Pressed: $task")
                    Snackbar.make(it, "\ud83e\udd70" + R.string.task_add_text, Snackbar.LENGTH_SHORT).show()
                }
            }
            setResult(RESULT_OK)
            finish()
        }

        binding.taskLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
            mapIntentLauncher.launch(launcherIntent)
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
            R.id.item_delete -> {
                //TODO call to alert dialog here
                AlertDialog.Builder(this)
                    .setTitle(R.string.alertDialog_DeleteTask)
                    .setMessage(R.string.alertDialog_ConfirmDelete)
                    .setPositiveButton(R.string.alertDialog_Yes) { _, _ ->
                        app.tasks.delete(task)
                        setResult(RESULT_OK)
                        finish()
                }
                    .setNegativeButton(R.string.alertDialog_No, null)
                    .show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}