package com.edvard.myfitnessfriend.page2.ui.top

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.edvard.myfitnessfriend.AppStat
import com.edvard.myfitnessfriend.R
import kotlinx.android.synthetic.main.fragment_month.*
import kotlinx.android.synthetic.main.fragment_weeks.date
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class Month : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_month, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var now = LocalDate.now()
        var strNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        var month:String = strNow[5].toString() + strNow[6].toString()
        var day:String = strNow[8].toString() + strNow[9].toString()
        var today:String = month + "월" + day + "일"
        date.text = today
        monthCalorie.text = AppStat.myStat.getMonthCal().toInt().toString()
        val hour = AppStat.myStat.getMonthTime().toInt()/3600
        val min = AppStat.myStat.getMonthTime().toInt()/60 - hour * 60
        val sec = AppStat.myStat.getMonthTime().toInt()%60
        exerciseTime.text ="${hour}시간 ${min}분 ${sec}초"
    }
    companion object{
        private const val num = "3"

        fun newInstance(Number: Int) : Month {
            return Month().apply{
                arguments = Bundle().apply {
                    arguments = Bundle().apply {
                        putInt(num, Number)
                    }
                }
            }
        }
    }
}
