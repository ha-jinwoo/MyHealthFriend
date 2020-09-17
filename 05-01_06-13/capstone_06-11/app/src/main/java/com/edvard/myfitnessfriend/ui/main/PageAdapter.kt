package com.edvard.myfitnessfriend.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){

    private val items = ArrayList<Fragment>()
    init{
        items.add(ExercisePage.newInstance(1))
        items.add(StatisticsPage.newInstance(2))
        items.add(MyPage.newInstance(3))
    }
    override fun getItem(postion: Int): Fragment {
        return items[postion]
    }
    override fun getCount(): Int{
        return items.size
    }
}