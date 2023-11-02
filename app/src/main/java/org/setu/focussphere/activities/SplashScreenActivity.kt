package org.setu.focussphere.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.VideoView
import org.setu.focussphere.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_NoActionBar)
        setContentView(R.layout.activity_splash_screen)

        val videoView = findViewById<VideoView>(R.id.videoViewSplash)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash_video_focussphere

        val videoUri = Uri.parse(videoPath)
        videoView.setVideoURI(videoUri)
        videoView.start()

        videoView.setOnCompletionListener {
            //when video playback is complete, then start next activity
            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}