package com.edvard.myfitnessfriend

class UserInfo(id: String){
    private var userId: String = ""
    private var isLogin: Boolean= false
    private var todayCal: Int = 0
    init{
        userId = id
    }
    fun getIsLogin():Boolean = isLogin
    fun getUserId():String = userId
    fun setIsLogin(v: Boolean){
        isLogin = v
    }
    fun setUserId(v: String){
        userId = v
    }
    fun getTodayCal():Int = todayCal
    fun setTodayCal(v : Int){
        todayCal = v
    }

}