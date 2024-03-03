package org.setu.focussphere.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.setu.focussphere.R
import org.setu.focussphere.databinding.ActivityTaskBinding
import org.setu.focussphere.main.FocusSphereApplication
import org.setu.focussphere.models.Location
import org.setu.focussphere.models.PriorityLevel
import org.setu.focussphere.models.TaskModel
import org.setu.focussphere.models.TaskStatus
import timber.log.Timber.Forest.i

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private var task = TaskModel()
    lateinit var app: FocusSphereApplication
    private var edit = false

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            task.lat = location.lat
                            task.lng = location.lng
                            task.zoom = location.zoom
                        }
                    }
                    RESULT_CANCELED -> {} else -> {}
                }
            }
    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityTaskBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.toolbarAddTask.title = title
            setSupportActionBar(binding.toolbarAddTask)

            app = application as FocusSphereApplication
            i("Task Activity Started")

            registerMapCallback()

            val priorityAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, PriorityLevel.values())
            priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.prioritySpinner.adapter = priorityAdapter

            val statusAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, TaskStatus.values())
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

                if (task.title.isBlank()) {
                    Snackbar.make(it, R.string.snackbar_warning_missingTitle, Snackbar.LENGTH_LONG)
                        .show()
                } else if (!edit && app.tasks.findAll()
                        .any { it.title == task.title && it.description == task.description }
                ) {
                    Snackbar.make(
                        it,
                        R.string.snackbar_warning_duplicateTask,
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    if (edit) {
                        app.tasks.update(task.copy())
                    } else {
                        app.tasks.create(task.copy())
                        binding.taskTitle.text.clear()
                        binding.taskDescription.text.clear()
                        i("Button Pressed: $task")
                        Snackbar.make(
                            it,
                            "\ud83e\udd70" + R.string.task_add_text,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
                setResult(RESULT_OK)
                finish()
            }

            binding.taskLocation.setOnClickListener {
                val location = Location(52.8359, -6.9325, 15f)
                if (task.zoom != 0f) {
                    location.lat = task.lat
                    location.lng = task.lng
                    location.zoom = task.zoom
                }
                val launcherIntent = Intent(this, MapActivity::class.java)
                    .putExtra("location", location)
                mapIntentLauncher.launch(launcherIntent)
            }
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_add_task, menu)
            if (edit) if (menu != null) {
                menu.getItem(1).isVisible = true
            }
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.item_cancel -> {
                    finish()
                }

                R.id.item_delete -> {
                    AlertDialog.Builder(this)
                        .setTitle(R.string.alertDialog_DeleteTask)
                        .setMessage(R.string.alertDialog_ConfirmDelete)
                        .setPositiveButton(R.string.alertDialog_Yes) { _, _ ->
                            setResult(99)
                            app.tasks.delete(task)
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