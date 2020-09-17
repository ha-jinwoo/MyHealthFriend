package com.edvard.myfitnessfriend

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.data.Entry
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList


class LoginActivity : AppCompatActivity() {
    var userID2: String = ""
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
                            userID2 = jsonObject.getString("userID")
                            val userPass = jsonObject.getString("userPassword")
                            Toast.makeText(applicationContext, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                            AppStat.myStat.setIsLogin(true)
                            AppStat.myStat.setUserId(userID2)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("userID", userID2)
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
                    AppStat.friendList.add(User(item.toString()))
                }
            } catch (e: JSONException) {
                Toast.makeText(this,"데이터를 불러오지 못했습니다.",Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
            val responseListener3 = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        val one = jsonObject.getString("one").toFloat()
                        val two = jsonObject.getString("two").toFloat()
                        val three = jsonObject.getString("three").toFloat()
                        val four = jsonObject.getString("four").toFloat()
                        val five = jsonObject.getString("five").toFloat()
                        val six = jsonObject.getString("six").toFloat()
                        val seven = jsonObject.getString("seven").toFloat()
                        var values = ArrayList<Entry>()
                        Toast.makeText(this,six.toString(),Toast.LENGTH_SHORT).show()

                        values.add(Entry(1.toFloat(),one))
                        values.add(Entry(2.toFloat(),two))
                        values.add(Entry(3.toFloat(),three))
                        values.add(Entry(4.toFloat(),four))
                        values.add(Entry(5.toFloat(),five))
                        values.add(Entry(6.toFloat(),six))
                        values.add(Entry(7.toFloat(),seven))

                        AppStat.myStat.setWeekCalorie(values)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            Toast.makeText(this,userID,Toast.LENGTH_SHORT).show()
            val responseListener4 = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        AppStat.myStat.setTodayCal(jsonObject.getString("userCALORIE").toFloat())
                        Toast.makeText(this, jsonObject.getString("userCALORIE"), Toast.LENGTH_SHORT).show()
                    } else {
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            val loginRequest = LoginRequest(userID, userPass, responseListener)
            val queue: RequestQueue = Volley.newRequestQueue(this@LoginActivity)

            val getFriendRequest = GetFriendRequest(userID,responseListener2)
            queue.add(loginRequest)
            queue.add(getFriendRequest)

            val getWeekCalorieRequest = GetWeekCalorieRequest(userID, responseListener3)
            queue.add(getWeekCalorieRequest)

            val getFriendCalorieRequest = GetFriendCalorieRequest(userID, responseListener4)
            queue.add(getFriendCalorieRequest)

        })
    }
    override fun onBackPressed() {
        val mintent = Intent(this, MainActivity::class.java)
        startActivity(mintent)
        finish()
    }
}