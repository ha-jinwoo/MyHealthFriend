package com.edvard.myfitnessfriend

import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.edvard.myfitnessfriend.activities.MainActivity
import com.github.mikephil.charting.data.Entry
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class DB{
    companion object {
        //로그인
        fun loginRequest(context: Context, queue: RequestQueue, data: HashMap<String, Any>): Boolean {
            var ret: Boolean = true
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        Toast.makeText(context, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                        AppStat.myStat.setIsLogin(true)
                        AppStat.myStat.setUserWeight(jsonObject.getString("userWeight").toFloat())
                        AppStat.myStat.setUserId(jsonObject.getString("userID"))
                    } else {
                        Toast.makeText(context, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        ret = false
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("login")
            val loginRequest = DBRequest(data, responseListener)
            queue.addRequestFinishedListener<String> {
                if(ret){
                    val newQueue = makeNewRequestQueue(context)
                    val weekQueue = makeNewRequestQueue(context)
                    weekQueue.addRequestFinishedListener<String> {
                        val mintent = Intent(context, MainActivity::class.java)
                        context.startActivity(mintent)
                    }
                    weekCalorieRequest(context, weekQueue, data)
                    weekTimeRequest(context, weekQueue, data)
                    friendListRequest(context, newQueue, data)
                    myCalorieRequest(context, newQueue, data)
                    getTimeRequest(context, newQueue, data)
                    getMonthCalorieRequest(context, newQueue, data)
                    getMonthTimeRequest(context,newQueue,data)
                    getTotalCalorieRequest(context,newQueue,data)
                    getTotalTimeRequest(context,newQueue,data)
                }
            }
            queue.add(loginRequest)

            return ret
        }
        //친구 목록 불러오기
        fun friendListRequest(context: Context, queue:RequestQueue, data: HashMap<String, Any>): Boolean {
            var ret: Boolean = true
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONArray(response)
                    for (i in 0 until jsonObject.length()) {
                        val item = jsonObject.getString(i)
                        AppStat.friendList.add(User(item.toString()))
                    }
                } catch (e: JSONException) {
                    Toast.makeText(context, "데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("getFriend")
            val getFriendRequest = DBRequest(data, responseListener)
            queue.add(getFriendRequest)
            return ret
        }
        //일주일 칼로리
        fun weekCalorieRequest(context: Context, queue: RequestQueue,data: HashMap<String, Any>):Boolean{
            var ret: Boolean = true
            val responseListener= Response.Listener<String?> { response ->
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
                        //Toast.makeText(context,six.toString(),Toast.LENGTH_SHORT).show()

                        values.add(Entry(1.toFloat(),one))
                        values.add(Entry(2.toFloat(),two))
                        values.add(Entry(3.toFloat(),three))
                        values.add(Entry(4.toFloat(),four))
                        values.add(Entry(5.toFloat(),five))
                        values.add(Entry(6.toFloat(),six))
                        values.add(Entry(7.toFloat(),seven))
                        values.add(Entry(8f,0f))
                        AppStat.myStat.setWeekCalorie(values)
                        AppStat.myStat.setWeekCal(one+two+three+four+five+six+seven)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("weekCalorie")
            val getWeekCalorieRequest = DBRequest(data, responseListener)
            queue.add(getWeekCalorieRequest)

            return ret
        }
        //일주일 시간
        fun weekTimeRequest(context: Context, queue: RequestQueue,data: HashMap<String, Any>):Boolean{
            var ret: Boolean = true
            val responseListener= Response.Listener<String?> { response ->
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
                        //Toast.makeText(context,six.toString(),Toast.LENGTH_SHORT).show()

                        values.add(Entry(1.toFloat(),one))
                        values.add(Entry(2.toFloat(),two))
                        values.add(Entry(3.toFloat(),three))
                        values.add(Entry(4.toFloat(),four))
                        values.add(Entry(5.toFloat(),five))
                        values.add(Entry(6.toFloat(),six))
                        values.add(Entry(7.toFloat(),seven))
                        values.add(Entry(8f,0f))
                        AppStat.myStat.setWeekTimelist(values)
                        AppStat.myStat.setWeekTime(one+two+three+four+five+six+seven)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("weekTime")
            val getWeekTimeRequest = DBRequest(data, responseListener)
            queue.add(getWeekTimeRequest)

            return ret
        }
        //친구 일주일 칼로리
        fun friendWeekCalorieRequest(context: Context, queue: RequestQueue,data: HashMap<String, Any>):Boolean{
            var ret: Boolean = true
            val responseListener= Response.Listener<String?> { response ->
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
                        //Toast.makeText(context,six.toString(),Toast.LENGTH_SHORT).show()

                        values.add(Entry(1.toFloat(),one))
                        values.add(Entry(2.toFloat(),two))
                        values.add(Entry(3.toFloat(),three))
                        values.add(Entry(4.toFloat(),four))
                        values.add(Entry(5.toFloat(),five))
                        values.add(Entry(6.toFloat(),six))
                        values.add(Entry(7.toFloat(),seven))
                        values.add(Entry(8f,0f))
                        AppStat.myStat.setFriendWeekCalorie(values)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("weekCalorie")
            val getWeekCalorieRequest = DBRequest(data, responseListener)
            queue.add(getWeekCalorieRequest)

            return ret
        }
        //나의  하루 칼로리
        fun myCalorieRequest(context: Context, queue:RequestQueue,data: HashMap<String, Any>):Boolean{
            var ret:Boolean = true
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        AppStat.myStat.setTodayCal(jsonObject.getString("userCALORIE").toFloat())
                        //Toast.makeText(context, jsonObject.getString("userCALORIE"), Toast.LENGTH_SHORT).show()
                    } else {
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("myCalorie")
            val getFriendCalorieRequest = DBRequest(data, responseListener)
            queue.add(getFriendCalorieRequest)
            return ret
        }
        //회원가입
        fun registerRequest(context: Context, queue: RequestQueue, data: HashMap<String, Any>): Boolean{
            var ret: Boolean = true
            val responseListener = Response.Listener<String?>{ response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        Toast.makeText(context, "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("register")
            val registerRequest = DBRequest(data, responseListener)
            queue.add(registerRequest)

            return ret
        }
        //소모 칼로리 보내기
        fun postCalorieRequest(context: Context, queue:RequestQueue,data: HashMap<String, Any>):Boolean{
            var ret:Boolean = true
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        //Toast.makeText(context, jsonObject.getString("userCALORIE"), Toast.LENGTH_SHORT).show()
                    } else {
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("postCalorie")
            val postCalorieRequest = DBRequest(data, responseListener)
            queue.add(postCalorieRequest)
            return ret
        }
        //시간 보내기
        fun postTimeRequest(context: Context, queue:RequestQueue,data: HashMap<String, Any>):Boolean{
            var ret:Boolean = true
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        //Toast.makeText(context, jsonObject.getString("userCALORIE"), Toast.LENGTH_SHORT).show()
                    } else {
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("postTime")
            val postTimeRequest = DBRequest(data, responseListener)
            queue.add(postTimeRequest)
            return ret
        }

        fun getTimeRequest(context: Context, queue: RequestQueue, data: HashMap<String, Any>): Boolean {
            var ret: Boolean = true
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        AppStat.myStat.setTodayTime(jsonObject.getString("totaltime").toFloat())
                    } else {
                        ret = false
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("getTime")
            val getTimeRequest = DBRequest(data, responseListener)

            queue.add(getTimeRequest)

            return ret
        }
        fun getMonthCalorieRequest(context: Context, queue: RequestQueue, data: HashMap<String, Any>): Boolean {
            var ret: Boolean = true
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        AppStat.myStat.setMonthCal(jsonObject.getString("monthCALORIE").toFloat())
                    } else {
                        ret = false
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("getMonthCalorie")
            val getMonthCalorieRequest = DBRequest(data, responseListener)

            queue.add(getMonthCalorieRequest)

            return ret
        }
        fun getMonthTimeRequest(context: Context, queue: RequestQueue, data: HashMap<String, Any>): Boolean {
            var ret: Boolean = true
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        AppStat.myStat.setMonthTime(jsonObject.getString("monthTOTALTIME").toFloat())
                    } else {
                        ret = false
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("getMonthTime")
            val getMonthTimeRequest = DBRequest(data, responseListener)

            queue.add(getMonthTimeRequest)

            return ret
        }
        fun getTotalCalorieRequest(context: Context, queue: RequestQueue, data: HashMap<String, Any>): Boolean {
            var ret: Boolean = true
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        AppStat.myStat.setTotalCal(jsonObject.getString("totalcalorie").toFloat())
                    } else {
                        ret = false
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("getTotalCalorie")
            val getTotalCalorieRequest = DBRequest(data, responseListener)

            queue.add(getTotalCalorieRequest)

            return ret
        }
        fun getTotalTimeRequest(context: Context, queue: RequestQueue, data: HashMap<String, Any>): Boolean {
            var ret: Boolean = true
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        AppStat.myStat.setTotalTime(jsonObject.getString("totaltime").toFloat())
                    } else {
                        ret = false
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("getTotalTime")
            val getTotalTimeRequest = DBRequest(data, responseListener)

            queue.add(getTotalTimeRequest)

            return ret
        }

        //친구 하루 칼로리
        fun friendCalorieRequest(context: Context, queue: RequestQueue, data: HashMap<String, Any>){
            var calorie : String = "0"
            val responseListener = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        data["calorie"] = jsonObject.getString("userCALORIE")
                    } else {
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            DBRequest.setRequestType("friendCalorie")
            val getFriendCalorieRequest = DBRequest(data, responseListener)
            queue.add(getFriendCalorieRequest)
        }

        fun makeNewRequestQueue(context: Context) : RequestQueue = Volley.newRequestQueue(context)
    }





}