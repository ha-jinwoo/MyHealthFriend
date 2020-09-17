package com.edvard.myfitnessfriend

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        val thisintent = intent
        var videoLink: String = ""

        when(thisintent.getStringExtra("typeOfExercise")){
            "squat" -> videoLink = "lnJPcQwRURY"
            "lunge" -> videoLink = "oYiBDWhmrX8"
            "standingCrunch" ->  videoLink = "nSzcY0NaXLM"
        }
        tutorialVideo.play(videoLink)
        startButton.setOnClickListener {
            val myIntent = Intent(this, CameraActivity::class.java)
            myIntent.putExtra("typeOfExercise",thisintent.getStringExtra("typeOfExercise"))
            startActivity(myIntent)
            finish()
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}