package org.setu.focussphere.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.setu.focussphere.R
import org.setu.focussphere.adapters.TaskAdapter
import org.setu.focussphere.adapters.TaskListener
import org.setu.focussphere.databinding.ActivityTaskListBinding
import org.setu.focussphere.main.FocusSphereApplication
import org.setu.focussphere.models.TaskModel


class TaskListActivity : AppCompatActivity(), TaskListener {

    lateinit var app: FocusSphereApplication
    private lateinit var binding: ActivityTaskListBinding
    private var position: Int = 0

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )    { }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as FocusSphereApplication

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = TaskAdapter(app.tasks.findAll(), this)

    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, TaskActivity:: class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, TaskMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                refreshTaskList()
            }
        }

    override fun onTaskClick(task: TaskModel, pos : Int) {
        val launcherIntent = Intent(this, TaskActivity::class.java)
        launcherIntent.putExtra("task_edit", task)
        position = pos
        getClickResult.launch(launcherIntent)
    }

//    private val getClickResult =
//        registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) {
//            if (it.resultCode == Activity.RESULT_OK) {
//                refreshTaskList()
//            }
//        }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.tasks.findAll().size)
            }
            else // Deleting
                if (it.resultCode == 99)
                        (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }

    private fun refreshTaskList() {
        val updatedTasks = app.tasks.findAll()
        (binding.recyclerView.adapter as? TaskAdapter)?.updateTasks(updatedTasks)
        }



}


