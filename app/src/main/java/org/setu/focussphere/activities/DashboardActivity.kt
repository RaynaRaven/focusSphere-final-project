package org.setu.focussphere.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.setu.focussphere.R
import org.setu.focussphere.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tasksDashboardInfo.setOnClickListener() {
            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)
        }
    }
}