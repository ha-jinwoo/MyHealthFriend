package com.edvard.myfitnessfriend

import com.github.mikephil.charting.data.Entry

class User(id: String){
    private var userId: String = ""
    private var isLogin: Boolean= false
    //오늘 소모한 칼로리 시간.
    private var todayCal: Float = 0f
    private var todayTime: Float = 0f
    //일주일 소모한 칼로리 시간.
    private var weekCal: Float = 0f
    private var weekTime: Float = 0f
    //한달 소모한 칼로리 시간.
    private var monthCal: Float = 0f
    private var monthTime: Float = 0f
    //총 소모한 칼로리 시간.
    private var totalCal: Float = 0f
    private var totalTime: Float = 0f

    private var userWeight: Float = 0f

    private var weekCalorie = ArrayList<Entry>()
    private var weekTimeList = ArrayList<Entry>()
    private var friendweekCalorie = ArrayList<Entry>()
    private var exerciseTime = HashMap<String, Double>()

    init{
        userId = id
    }
    //getter
    fun getIsLogin():Boolean = isLogin
    fun getUserId():String = userId
    fun getUserWeight():Float = userWeight



    fun getTodayCal():Float = todayCal
    fun getTodayTime():Float = todayTime

    fun getWeekCal():Float = weekCal
    fun getWeekTime():Float = weekTime

    fun gettotalCal():Float = totalCal
    fun gettotalTime():Float = totalTime

    fun getMonthCal():Float = monthCal
    fun getMonthTime():Float = monthTime

    fun getWeekCalorie():ArrayList<Entry> = weekCalorie
    fun getWeekTimeList():ArrayList<Entry> = weekTimeList
    fun getFriendWeekCalorie():ArrayList<Entry> = friendweekCalorie
    //setter
    fun setIsLogin(v: Boolean){
        isLogin = v
    }
    fun setUserId(v: String){
        userId = v
    }

    fun setUserWeight(v: Float){
        userWeight = v
    }
    fun setTodayTime(v: Float){
        todayTime = v
    }
    fun setTodayCal(v : Float){
        todayCal = v
    }
    fun setWeekCal(v : Float){
        weekCal = v
    }
    fun setWeekTime(v : Float){
        weekTime = v
    }
    fun setMonthCal(v : Float){
        monthCal = v
    }
    fun setMonthTime(v : Float){
        monthTime = v
    }
    fun setTotalCal(v : Float){
        totalCal = v
    }
    fun setTotalTime(v : Float){
        totalTime = v
    }
    fun addTodayCal(v: Float){
        todayCal += v
    }
    fun addTodayTime(v: Float){
        todayTime += v
    }
    fun addWeekCal(v: Float){
        weekCal += v
    }
    fun addWeekTime(v: Float){
        weekTime += v
    }
    fun addMonthCal(v: Float){
        monthCal += v
    }
    fun addMonthTime(v: Float){
        monthTime += v
    }
    fun addTotalCal(v: Float){
        totalCal += v
    }
    fun addTotalTime(v: Float){
        totalTime += v
    }
    fun setWeekCalorie(v: ArrayList<Entry>){
        weekCalorie = v
    }
    fun setWeekTimelist(v: ArrayList<Entry>){
        weekTimeList = v
    }
    fun setFriendWeekCalorie(v: ArrayList<Entry>){
        friendweekCalorie = v
    }

}