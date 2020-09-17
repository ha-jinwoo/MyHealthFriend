package com.edvard.poseestimation

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.*

class LoginRequest(userID: String, userPassword: String, listener: Response.Listener<String?>?) : StringRequest(Method.POST, URL, listener, null) {
    private val map: MutableMap<String, String>

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return map
    }

    companion object {
        private const val URL = "http://http://gkwlsdn95.dothome.co.kr/Register.php"
    }

    init {
        map = HashMap()
        map["userID"] = userID
        map["userPassword"] = userPassword
    }
}