package com.edvard.myfitnessfriend.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edvard.myfitnessfriend.AppStat
import com.edvard.myfitnessfriend.Chart
import com.edvard.myfitnessfriend.R
import kotlinx.android.synthetic.main.activity_friend_statistics.*

class FriendStatisticsActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_statistics)

        Chart.drawChart(linechart1, AppStat.myStat.getFriendWeekCalorie(), "날짜", "칼로리")
    }
}