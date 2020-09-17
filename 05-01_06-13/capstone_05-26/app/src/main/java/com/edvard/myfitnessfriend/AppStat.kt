package com.edvard.myfitnessfriend

class AppStat {

    companion object{
        var myStat:UserInfo = UserInfo("")
        var friendList = ArrayList<UserInfo>()
        var firstLogin: Boolean = true
        fun clear(){
            myStat.setUserId("")
            myStat.setIsLogin(false)
            friendList.clear()
        }
    }
}