package com.edvard.myfitnessfriend.page2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edvard.myfitnessfriend.AppStat
import com.edvard.myfitnessfriend.R
import kotlinx.android.synthetic.main.fragment_today.*

class Today : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_today, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todayCal.text = AppStat.myStat.getTodayCal().toString()
    }
    companion object{
        private const val num = "1"

        fun newInstance(Number: Int) : Today{
            return Today().apply{
                arguments = Bundle().apply {
                    arguments = Bundle().apply {
                        putInt(num, Number)
                    }
                }
            }
        }
    }
}
