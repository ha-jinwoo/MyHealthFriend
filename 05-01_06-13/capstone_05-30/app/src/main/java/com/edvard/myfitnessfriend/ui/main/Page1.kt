package com.edvard.myfitnessfriend.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.edvard.myfitnessfriend.R
import com.edvard.myfitnessfriend.TutorialActivity
import kotlinx.android.synthetic.main.fragment_page1.*


class Page1 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_page1, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(context, TutorialActivity::class.java)
        squatsButton.setOnClickListener{
            //val intent = Intent(context, SquatsActivity::class.java)
            intent.putExtra("typeOfExercise","squat")
            startActivity(intent)
            activity?.finish()
        }
        lungeButton.setOnClickListener{
            //val intent = Intent(context, LungeActivity::class.java)
            intent.putExtra("typeOfExercise","lunge")
            startActivity(intent)
            activity?.finish()
        }
        standingCrunchButton.setOnClickListener {
            intent.putExtra("typeOfExercise","standingCrunch")
            startActivity(intent)
            activity?.finish()
        }
    }

    companion object{
        private const val num = "2"

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
