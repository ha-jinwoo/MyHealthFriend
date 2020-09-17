package com.edvard.myfitnessfriend.ui.main

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edvard.myfitnessfriend.Chart


import com.edvard.myfitnessfriend.R
import com.edvard.myfitnessfriend.page2.ui.StatisticsAdapter
import com.edvard.myfitnessfriend.page2.ui.WeekStaticAdapter
import kotlinx.android.synthetic.main.fragment_page2.*

class Page2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_page2, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = StatisticsAdapter(activity!!.supportFragmentManager)
        viewPager.currentItem = 0

        bottomViewPager.adapter = WeekStaticAdapter(activity!!.supportFragmentManager)
        bottomViewPager.currentItem = 0
    }
    companion object{
        private const val num = "1"

        fun newInstance(Number: Int) : Page2{
            return Page2().apply{
                arguments = Bundle().apply {
                    arguments = Bundle().apply {
                        putInt(num, Number)
                    }
                }
            }
        }
    }
}
