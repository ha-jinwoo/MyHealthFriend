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

package com.edvard.myfitnessfriend

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

import org.opencv.android.BaseLoaderCallback
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader

/**
 * Main `Activity` class for the Camera app.
 */
class CameraActivity : AppCompatActivity() {

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
    Toast.makeText(this, typeOfExercise, Toast.LENGTH_SHORT).show()
    Posestimation.curExercise = typeOfExercise
    Posestimation.init()
    if (null == savedInstanceState) {
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
    intent.putExtra("typeOfExercise",Posestimation.curExercise)
    val userID = AppStat.myStat.getUserId()
    val responseListener = Response.Listener<String?>{ response ->
      try {
        val jsonObject = JSONObject(response)
        val success = jsonObject.getBoolean("success")
        if (success) {
          Toast.makeText(applicationContext, "소모된 칼로리가 증가하였습니다.", Toast.LENGTH_SHORT).show()
        } else {
          Toast.makeText(applicationContext, "칼로리 증가에 실패하였습니다.", Toast.LENGTH_SHORT).show()
          return@Listener
        }
      } catch (e: JSONException) {
        e.printStackTrace()
      }
    }
    val postCalorieRequest = PostCalorieRequest(userID, 10, responseListener)
    val queue: RequestQueue = Volley.newRequestQueue(this@CameraActivity)
    queue.add(postCalorieRequest)
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
}
