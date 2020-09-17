package com.edvard.myfitnessfriend

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_friend_statistics.*

class FriendStatisticsActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_statistics)

        Chart.drawChart(linechart1, AppStat.myStat.getFriendWeekCalorie(), "날짜","칼로리")
    }
}