/*
 * Copyright 2018 Zihua Zeng (edvard_hua@live.com), Lang Feng (tearjeaker@hotmail.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.edvard.myfitnessfriend.openpose

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.edvard.myfitnessfriend.AppStat
import com.edvard.myfitnessfriend.DB
import com.edvard.myfitnessfriend.Posestimation
import com.edvard.myfitnessfriend.R
import com.edvard.myfitnessfriend.activities.MainActivity
import com.edvard.myfitnessfriend.activities.TutorialActivity
import kotlinx.android.synthetic.main.activity_login.*

import org.opencv.android.BaseLoaderCallback
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader

/**
 * Main `Activity` class for the Camera app.
 */
class CameraActivity : AppCompatActivity() {
  var startTime: Long = 0
  var endTime:  Long = 0
  private val mLoaderCallback = object : BaseLoaderCallback(this) {
    override fun onManagerConnected(status: Int) {
      when (status) {
        LoaderCallbackInterface.SUCCESS -> isOpenCVInit = true
        LoaderCallbackInterface.INCOMPATIBLE_MANAGER_VERSION -> {
        }
        LoaderCallbackInterface.INIT_FAILED -> {
        }
        LoaderCallbackInterface.INSTALL_CANCELED -> {
        }
        LoaderCallbackInterface.MARKET_ERROR -> {
        }
        else -> {
          super.onManagerConnected(status)
        }
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_camera)
    val thisIntent = intent
    val typeOfExercise : String = thisIntent.getStringExtra("typeOfExercise")
    //Toast.makeText(this, typeOfExercise, Toast.LENGTH_SHORT).show()
    Posestimation.curExercise = typeOfExercise
    if (null == savedInstanceState) {
      when(Posestimation.curExercise){
        "squat" -> Play_Intro(this, R.raw.squat_intro)
        "lunge" -> Play_Intro(this, R.raw.lunge_intro)
        "standingCrunch" -> Play_Intro(this, R.raw.standingcrunch_intro)
        "plank" -> Play_Intro(this, R.raw.plank_intro)
      }
      supportFragmentManager.beginTransaction().replace(R.id.container, Camera2BasicFragment.newInstance()).commit()
      /*fragmentManager
          .beginTransaction()
          .replace(R.id.container, Camera2BasicFragment.newInstance())
          .commit()*/
    }
  }

  override fun onResume() {
    super.onResume()
    if (!OpenCVLoader.initDebug()) {
      OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback)
    } else {
      mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
    }
  }

  override fun onBackPressed() {
    val intent = Intent(this, TutorialActivity::class.java)
    endTime = System.currentTimeMillis()
    intent.putExtra("typeOfExercise", Posestimation.curExercise)


    startActivity(intent)
    finish()
  }
  companion object {

    init {
      //        System.loadLibrary("opencv_java");
      System.loadLibrary("opencv_java3")
    }

    @JvmStatic
    var isOpenCVInit = false
  }

  private fun Play_Intro(context: Context, sound:Int){
    var Sound: MediaPlayer = MediaPlayer.create(context, sound)
    if(!Posestimation.isPlaying) {
      Sound.start()
      AppStat.introPlaying = true

      Handler().postDelayed({
        Sound.release()
        AppStat.introPlaying = false
        AppStat.exerciseStartTime = System.currentTimeMillis()
      }, Sound.duration.toLong())
    }
  }
}
