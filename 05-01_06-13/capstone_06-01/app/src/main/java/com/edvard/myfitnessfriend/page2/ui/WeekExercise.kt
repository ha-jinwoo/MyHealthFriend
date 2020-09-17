package com.edvard.myfitnessfriend.page2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edvard.myfitnessfriend.Chart

import com.edvard.myfitnessfriend.R
import kotlinx.android.synthetic.main.fragment_week_exercise.*

class WeekExercise : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_week_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val Data2 = Chart.makeChartData()
        Chart.drawChart(linechart2, Data2,"날짜","운동횟수")
    }
    companion object{
        private const val num = "1"

        fun newInstance(Number: Int) : WeekExercise{
            return WeekExercise().apply{
                arguments = Bundle().apply {
                    arguments = Bundle().apply {
                        putInt(num, Number)
                    }
                }
            }
        }
    }


}
