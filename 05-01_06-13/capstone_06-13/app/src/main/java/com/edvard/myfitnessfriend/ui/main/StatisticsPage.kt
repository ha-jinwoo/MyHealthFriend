package com.edvard.myfitnessfriend.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.edvard.myfitnessfriend.R
import com.edvard.myfitnessfriend.page2.ui.top.StatisticsAdapter
import com.edvard.myfitnessfriend.page2.ui.bottom.WeekStaticAdapter
import kotlinx.android.synthetic.main.fragment_statistics_page.*

class StatisticsPage : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_statistics_page, container, false)
        mcontext = context
        return root
    }
    override fun onResume() {
        super.onResume()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = StatisticsAdapter(activity!!.supportFragmentManager)
        viewPager.currentItem = 0

        bottomViewPager.adapter = WeekStaticAdapter(activity!!.supportFragmentManager)
        bottomViewPager.currentItem = 0


    }
    companion object{
        var mcontext: Context? = null
        private const val num = "1"

        fun newInstance(Number: Int) : StatisticsPage{
            return StatisticsPage().apply{
                arguments = Bundle().apply {
                    arguments = Bundle().apply {
                        putInt(num, Number)
                    }
                }
            }
        }
    }
}
