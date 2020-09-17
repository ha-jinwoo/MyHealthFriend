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
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject



class LoginActivity : AppCompatActivity() {
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
                            AppStat.myStat.setIsLogin(true)
                            AppStat.myStat.setUserId(userID)
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
            val responseListener2 = Response.Listener<String?> { response ->
            try {
                val jsonObject = JSONArray(response)
                for (i in 0 until jsonObject.length()) {
                    val item = jsonObject.getString(i)
                    AppStat.friendList.add(UserInfo(item.toString()))
                }
            } catch (e: JSONException) {
                Toast.makeText(this,"데이터를 불러오지 못했습니다.",Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

            val loginRequest = LoginRequest(userID, userPass, responseListener)
            val queue: RequestQueue = Volley.newRequestQueue(this@LoginActivity)

            val getFriendRequest = GetFriendRequest(userID,responseListener2)
            queue.add(loginRequest)
            queue.add(getFriendRequest)
        })
    }
    override fun onBackPressed() {
        val mintent = Intent(this, MainActivity::class.java)
        startActivity(mintent)
        finish()
    }
}