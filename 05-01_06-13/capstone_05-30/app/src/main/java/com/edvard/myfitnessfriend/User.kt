package com.edvard.myfitnessfriend

import com.github.mikephil.charting.data.Entry

class User(id: String){
    private var userId: String = ""
    private var isLogin: Boolean= false
    private var todayCal: Float = 0f
    private var todayTime: Float = 0f
    private var weekCalorie = ArrayList<Entry>()
    private var friendweekCalorie = ArrayList<Entry>()

    init{
        userId = id
    }
    //getter
    fun getIsLogin():Boolean = isLogin
    fun getUserId():String = userId
    fun getTodayCal():Float = todayCal
    fun getWeekCalorie():ArrayList<Entry> = weekCalorie
    fun getFriendWeekCalorie():ArrayList<Entry> = friendweekCalorie
    //setter
    fun setIsLogin(v: Boolean){
        isLogin = v
    }
    fun setUserId(v: String){
        userId = v
    }
    fun setTodayTime(v: Float){
        todayTime = v
    }
    fun setTodayCal(v : Float){
        todayCal = v
    }
    fun setWeekCalorie(v: ArrayList<Entry>){
        weekCalorie = v
    }
    fun setFriendWeekCalorie(v: ArrayList<Entry>){
        friendweekCalorie = v
    }

}