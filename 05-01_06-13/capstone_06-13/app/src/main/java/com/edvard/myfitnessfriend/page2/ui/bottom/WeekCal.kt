package com.edvard.myfitnessfriend.page2.ui.bottom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edvard.myfitnessfriend.AppStat
import com.edvard.myfitnessfriend.Chart

import com.edvard.myfitnessfriend.R
import kotlinx.android.synthetic.main.fragment_seven_week_cal.*

class WeekCal : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seven_week_cal, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data1 = AppStat.myStat.getWeekCalorie()
        Chart.drawChart(linechart1, data1,"날짜", "칼로리")

    }
    companion object{
        private const val num = "2"

        fun newInstance(Number: Int) : WeekCal {
            return WeekCal().apply{
                arguments = Bundle().apply {
                    arguments = Bundle().apply {
                        putInt(num, Number)
                    }
                }
            }
        }
    }

}
