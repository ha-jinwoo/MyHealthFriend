package com.edvard.myfitnessfriend

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class DBRequest(data: HashMap<String, Any>, listener: Response.Listener<String?>?)
    : StringRequest(Method.POST, URL, listener, null)  {
    private val map: MutableMap<String, String>
    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return map
    }
    companion object {
        //서버 URL 설정(PHP 파일 연동)
        private var URL = ""
        private var requestType = ""
        fun setRequestType(type: String){
            requestType = type
            when(type){
                "register" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/Register.php"
                }
                "login" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/Login.php"
                }
                "addFriend" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/Friend.php"
                }
                "weekCalorie" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/getWeekCalorie.php"
                }
                "weekTime" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/getWeekTime.php"
                }
                "friendCalorie" ->{
                    URL = "http://gkwlsdn95.dothome.co.kr/getFriendCalorie.php"
                }
                "myCalorie" ->{
                    URL = "http://gkwlsdn95.dothome.co.kr/getFriendCalorie.php"
                }
                "getFriend" ->{
                    URL =  "http://gkwlsdn95.dothome.co.kr/getFriend.php"
                }

                "postCalorie" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/postCalorie.php"
                }
                "postTime" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/postTime.php"
                }
                "getTime" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/getTime.php"
                }
                "getMonthCalorie" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/getMonthCalorie.php"
                }
                "getMonthTime" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/getMonthTime.php"
                }
                "getTotalCalorie" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/getTotalCalorie.php"
                }
                "getTotalTime" -> {
                    URL = "http://gkwlsdn95.dothome.co.kr/getTotalTime.php"
                }
            }
        }
    }

    init {
        map = HashMap()
        map["userID"] = data["userID"].toString()
        when(requestType){
            "login" ->{
                //URL = "http://gkwlsdn95.dothome.co.kr/Login.php"
                map["userPassword"] = data["userPassword"].toString()
            }
            "addFriend" -> {
                //URL ="http://gkwlsdn95.dothome.co.kr/Friend.php"
                map["userFRIENDID"] = data["userFRIENDID"].toString()
            }
            "weekCalorie" -> {
                //URL = "http://gkwlsdn95.dothome.co.kr/getWeekCalorie.php"
            }
            "weekTime" -> {
                //URL = "http://gkwlsdn95.dothome.co.kr/getWeekTime.php"
            }
            "friendCalorie" ->{
                // URL = "http://gkwlsdn95.dothome.co.kr/getFriendCalorie.php"
            }
            "myCalorie" ->{
                //URL = "http://gkwlsdn95.dothome.co.kr/getFriendCalorie.php"
            }
            "getFriend" ->{
                //URL =  "http://gkwlsdn95.dothome.co.kr/getFriend.php"
            }
            "register" ->{
                map["userPassword"] = data["userPassword"].toString()
                map["userName"] = data["userName"].toString()
                map["userWeight"] = data["userWeight"].toString()+""
            }
            "postCalorie" -> {
                //URL = "http://gkwlsdn95.dothome.co.kr/postCalorie.php"
                map["userCalorie"] = data["userCalorie"].toString()+""
            }
            "postTime" -> {
                //URL = "http://gkwlsdn95.dothome.co.kr/postCalorie.php"
                map["totaltime"] = data["totaltime"].toString()+""
            }
            "getTime" -> {
                //URL = "http://gkwlsdn95.dothome.co.kr/getTime.php"
            }
            "getMonthCalorie" -> {
                //URL = "http://gkwlsdn95.dothome.co.kr/getMonthCalorie.php"
            }
            "getMonthTime" -> {
                //URL = "http://gkwlsdn95.dothome.co.kr/getMonthTime.php"
            }
            "getTotalCalorie" -> {
                //URL = "http://gkwlsdn95.dothome.co.kr/getTotalCalorie.php"
            }
            "getTotalTime" -> {
                //URL = "http://gkwlsdn95.dothome.co.kr/getTotalTime.php"
            }
        }
    }
}