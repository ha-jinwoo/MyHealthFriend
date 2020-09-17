package com.edvard.myfitnessfriend.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.edvard.myfitnessfriend.DB
import com.edvard.myfitnessfriend.R
import kotlinx.android.synthetic.main.activity_login.*


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

            val rQueue:RequestQueue = DB.makeNewRequestQueue(this)
            val data = HashMap<String, Any>()
            data["userID"] = userID
            data["userPassword"] = userPass
            rQueue.addRequestFinishedListener<String> {
                //val intent = Intent(this@LoginActivity, MainActivity::class.java)
                //startActivity(intent)
                finish()
            }
            DB.loginRequest(this, rQueue, data)

        })
    }
    override fun onBackPressed() {
        val mintent = Intent(this, MainActivity::class.java)
        startActivity(mintent)
        finish()
    }
}