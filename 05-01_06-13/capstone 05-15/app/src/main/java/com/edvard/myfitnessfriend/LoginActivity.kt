package com.edvard.myfitnessfriend

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject



class LoginActivity : AppCompatActivity() {
    private var lastBackPressed : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_register.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })
        btn_login.setOnClickListener(View.OnClickListener {
            val userID = et_id.getText().toString()
            val userPass = et_pass.getText().toString()
            val responseListener = Response.Listener<String?> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success) {
                            val userID = jsonObject.getString("userID")
                            val userPass = jsonObject.getString("userPassword")
                            Toast.makeText(applicationContext, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("userID", userID)
                            intent.putExtra("userPass", userPass)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                }
            }
            val loginRequest = LoginRequest(userID, userPass, responseListener)
            val queue: RequestQueue = Volley.newRequestQueue(this@LoginActivity)
            queue.add(loginRequest)
        })
    }
    override fun onBackPressed() {
        if(System.currentTimeMillis() - lastBackPressed >= 1500){
            lastBackPressed = System.currentTimeMillis()
            Toast.makeText(this, "한번더 누르면 종료해요.", Toast.LENGTH_SHORT).show()
        }
        else{
            finish()
        }
    }
}