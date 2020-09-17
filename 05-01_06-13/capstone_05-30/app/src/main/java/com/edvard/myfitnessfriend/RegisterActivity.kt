package com.edvard.myfitnessfriend

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btn_register.setOnClickListener(View.OnClickListener {
            val userID = et_id.getText().toString()
            val userPass = et_pass.getText().toString()
            val userName = et_name.getText().toString()
            val userAge = et_age.getText().toString().toInt()
            val responseListener = Response.Listener<String?>{ response ->

                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success) {
                            Toast.makeText(applicationContext, "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()

                }
            }
            val registerRequest = RegisterRequest(userID, userPass, userName, userAge, responseListener)
            val queue: RequestQueue = Volley.newRequestQueue(this@RegisterActivity)
            queue.add(registerRequest)
        })
    }
    override fun onBackPressed() {
        val mintent = Intent(this, LoginActivity::class.java)
        startActivity(mintent)
        finish()
    }

}
