package org.setu.focussphere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.setu.focussphere.databinding.ActivityTaskBinding
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

        binding.addTaskButton.setOnClickListener {
            val taskAddedText = getString(R.string.task_add_text)
            Toast.makeText(this, taskAddedText, Toast.LENGTH_SHORT).show()
        }
    }
}