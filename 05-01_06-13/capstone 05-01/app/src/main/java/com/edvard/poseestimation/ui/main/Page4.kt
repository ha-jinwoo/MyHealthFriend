package com.edvard.poseestimation.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.edvard.poseestimation.LoginRequest
import com.edvard.poseestimation.R
import com.edvard.poseestimation.RegisterRequest
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.fragment_page4.*
import org.json.JSONException
import org.json.JSONObject


class Page4 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.activity_registration, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_register.setOnClickListener {
            val userID = et_id.getText().toString()
            val userPass = et_pass.getText().toString()
            val userName = et_name.getText().toString()
            val userAge = et_age.getText().toString().toInt()
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        Toast.makeText(context, "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, Page4::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            val registerRequest = RegisterRequest(userID, userPass, userName, userAge, responseListener)
            val queue = Volley.newRequestQueue(getActivity()?.getApplicationContext())
            queue.add(registerRequest)
        }

    }

    companion object{
        private const val num = "4"

        fun newInstance(Number: Int) : Page4{
            return Page4().apply{
                arguments = Bundle().apply {
                    arguments = Bundle().apply {
                        putInt(num, Number)
                    }
                }
            }
        }
    }
}
