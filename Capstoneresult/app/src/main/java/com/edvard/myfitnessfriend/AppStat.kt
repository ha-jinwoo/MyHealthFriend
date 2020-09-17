package com.edvard.myfitnessfriend


class AppStat {

    companion object{
        var myStat:User = User("")
        var friendList = ArrayList<User>()
        var introPlaying = false
        var currentExerciseTime: Long = 0

        var resExerciseTime: Long = 0
        var curCal: Int = 0
        var isExercising:Boolean = false
        var canResultDialog:Boolean = false

        var exerciseStartTime: Long = 0

        fun clear(){
            myStat.setUserId("")
            myStat.setIsLogin(false)
            myStat.setTodayCal(0f)
            myStat.setTodayTime(0f)
            myStat.setWeekCal(0f)
            myStat.setWeekTime(0f)
            myStat.setMonthCal(0f)
            myStat.setMonthTime(0f)
            myStat.setTotalCal(0f)
            myStat.setTotalTime(0f)
            myStat.setUserWeight(0f)
            myStat.getWeekCalorie().clear()
            myStat.getWeekTimeList().clear()
            myStat.getFriendWeekCalorie().clear()
            currentExerciseTime = 0
            isExercising = false
            //exerciseFinished = false
            exerciseStartTime = 0
            friendList.clear()
        }
    }
}