package com.edvard.myfitnessfriend.page2.ui.top

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.edvard.myfitnessfriend.page2.ui.top.Month
import com.edvard.myfitnessfriend.page2.ui.top.Today
import com.edvard.myfitnessfriend.page2.ui.top.Weeks

class StatisticsAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){

    private val items = ArrayList<Fragment>()
    init{
        items.add(Today.newInstance(1))
        items.add(Weeks.newInstance(2))
        items.add(Month.newInstance(3))
    }
    override fun getItem(postion: Int): Fragment {
        return items[postion]
    }
    override fun getCount(): Int{
        return items.size
    }
}