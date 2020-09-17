package com.edvard.myfitnessfriend

class AppStat {

    companion object{
        var myStat:User = User("")
        var friendList = ArrayList<User>()
        var introPlaying = false
        fun clear(){
            myStat.setUserId("")
            myStat.setIsLogin(false)
            friendList.clear()
        }
    }
}