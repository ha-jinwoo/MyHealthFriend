package com.edvard.poseestimation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.edvard.poseestimation.CameraActivity

import com.edvard.poseestimation.R
import com.edvard.poseestimation.SquatsActivity
import kotlinx.android.synthetic.main.fragment_page2.*


class Page2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_page2, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        squatsButton.setOnClickListener{
            val intent = Intent(context, SquatsActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    companion object{
        private const val num = "2"

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
