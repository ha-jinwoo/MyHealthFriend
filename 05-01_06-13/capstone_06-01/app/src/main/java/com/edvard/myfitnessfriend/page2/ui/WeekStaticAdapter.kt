package com.edvard.myfitnessfriend.page2.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
class WeekStaticAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){

    private val items = ArrayList<Fragment>()
    init{
        items.add(WeekExercise.newInstance(1))
        items.add(WeekCal.newInstance(2))
    }
    override fun getItem(postion: Int): Fragment {
        return items[postion]
    }
    override fun getCount(): Int{
        return items.size
    }
}