package com.edvard.myfitnessfriend.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.edvard.myfitnessfriend.R
import com.edvard.myfitnessfriend.ui.main.PageAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private var lastBackPressed : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adpater = PageAdapter(supportFragmentManager)

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = adpater
        viewPager.currentItem = 0

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        //val tabTitle = arrayOf("운동", "통계", "마이페이지")
        val tabLogos = arrayOf(R.drawable.selector_bottom_exercise,
                R.drawable.selector_bottom_statistic,
                R.drawable.selector_bottom_main)
        for(i in 0..2) {
            //tabs.getTabAt(i)?.text = tabTitle[i]
            tabs.getTabAt(i)?.setIcon(tabLogos[i])
        }
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - lastBackPressed >= 1500){
            lastBackPressed = System.currentTimeMillis()
            Toast.makeText(this, "한번더 누르면 종료해요.", Toast.LENGTH_SHORT).show()
        }
        else{
            finish()
        }
    }
}