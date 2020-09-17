package com.edvard.myfitnessfriend.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.edvard.myfitnessfriend.AppStat
import com.edvard.myfitnessfriend.DB
import com.edvard.myfitnessfriend.Posestimation
import com.edvard.myfitnessfriend.openpose.CameraActivity
import com.edvard.myfitnessfriend.R
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
            "plank" -> videoLink = "Zq8nRY9P_cM"
        }
        tutorialVideo.play(videoLink)

        startButton.setOnClickListener {
            val myIntent = Intent(this, CameraActivity::class.java)
            myIntent.putExtra("typeOfExercise",thisintent.getStringExtra("typeOfExercise"))
            Posestimation.init(this)
            startActivity(myIntent)
            finish()

        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        val rQueue: RequestQueue = DB.makeNewRequestQueue(this)

        val data = HashMap<String, Any>()
        data["userID"] = AppStat.myStat.getUserId()
        data["totaltime"]=(AppStat.currentExerciseTime.toDouble()/1000).toInt()
        Toast.makeText(this,"${AppStat.currentExerciseTime.toDouble()/1000.0}" , Toast.LENGTH_SHORT).show()
        DB.postTimeRequest(this, rQueue, data)

        val data2 = HashMap<String, Any>()
        data2["userID"] = AppStat.myStat.getUserId()
        DB.getTimeRequest(this, rQueue, data)

        startActivity(intent)
        finish()
    }
}