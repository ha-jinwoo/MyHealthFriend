package com.edvard.myfitnessfriend.ui.main

import android.R.layout
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import com.edvard.myfitnessfriend.R
import com.edvard.myfitnessfriend.activities.TutorialActivity
import kotlinx.android.synthetic.main.fragment_exercise_page.*


class ExercisePage : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_exercise_page, container, false)
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
        plankButton.setOnClickListener {
            intent.putExtra("typeOfExercise", "plank")
            startActivity(intent)
            activity?.finish()
        }

    }

    companion object{
        private const val num = "2"

        fun newInstance(Number: Int) : ExercisePage{
            return ExercisePage().apply{
                arguments = Bundle().apply {
                    arguments = Bundle().apply {
                        putInt(num, Number)
                    }
                }
            }
        }
    }
}
