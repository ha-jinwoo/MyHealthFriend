package com.edvard.myfitnessfriend.ui.main

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edvard.myfitnessfriend.LoginActivity


import com.edvard.myfitnessfriend.R
import kotlinx.android.synthetic.main.fragment_page1.*

class Page1 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_page1, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testbtn.setOnClickListener{
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
    private fun Play_Sound(sound:Int){
        var Sound: MediaPlayer = MediaPlayer.create(context, sound)
        Sound?.start()
        Handler().postDelayed({
            Sound?.release()

        },1000)

    }
    companion object{
        private const val num = "1"

        fun newInstance(Number: Int) : Page1{
            return Page1().apply{
                arguments = Bundle().apply {
                    arguments = Bundle().apply {
                        putInt(num, Number)
                    }
                }
            }
        }
    }
}
