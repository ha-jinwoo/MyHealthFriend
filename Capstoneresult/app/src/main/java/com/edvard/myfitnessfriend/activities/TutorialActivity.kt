package com.edvard.myfitnessfriend.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.edvard.myfitnessfriend.AppStat
import com.edvard.myfitnessfriend.DB
import com.edvard.myfitnessfriend.Posestimation
import com.edvard.myfitnessfriend.openpose.CameraActivity
import com.edvard.myfitnessfriend.R
import kotlinx.android.synthetic.main.activity_tutorial.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class TutorialActivity : AppCompatActivity(){
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        val thisIntent = intent
        var videoLink: String = ""
        Posestimation.init(this)
        val squatExplain:String = "\n"+"운동 설명 및 효과\n" +
                "스쿼트는 하체운동 중에서도 가장 에너지를 많이 소비하는 운동이라 체지방 감소에도 효과가 큽니다.\n" +
                "적은 중량으로 고반복을 하거나 아예 빈 봉 스쿼트, 혹은 맨몸으로 하면 인터벌 유산소 운동으로도 적격입니다.\n" +
                "또한 하체 근력 발달과, 허리나 코어 근육은 물론 승모근 발달에도 도움이 되어 상체 단련에도 큰 도움을 줍니다.\n"

        val squatWarning:String ="\n"+"1. 정면을 바라보고 운동을 시작합니다.\n" +
                "2. 무릎이 발보다 앞으로 나오면 안됩니다.\n" +
                "3. 허리를 최대한 펴 요추말림 현상을 주의해야 합니다. \n" +
                "4. 자세를 잡을 때 발을 5~10도로 벌려 안정적인 자세를 취해야 합니다. \n" +
                "\n"
        val lungeExplain:String ="\n"+
                "운동 설명 및 효과\n" +
                "런지는 하체운동의 대명사로 불리며 스쿼트와 마찬가지로 효과가 좋은 운동입니다.\n" +
                "스쿼트가 근육 비대, 성기능 향상, 체력, 유연성 향상이 주된 운동 효과라 하면 \n" +
                "런지는 운동 신경과 균형감각, 허리 건강, 코어 강화를 위한 운동입니다.\n" +
                "런지는 다리 한 쪽을 앞으로 내밀며 무릎을 굽히되 무릎이 바닥에 닿지 않게 굽히는 것을 기본으로 합니다.\n"
        val lungeWarning ="\n"+
                "1. 왼쪽을 바라보고 운동을 시작합니다.\n" +
                "2. 무릎이 발보다 앞으로 나오면 안됩니다.\n" +
                "3. 허리를 완전히 피고 운동을 해야 합니다.\n" +
                "4. 어느정도 앞으로 나가 무릎이 90도로 굽혀질 때 까지 상체가 내려가야 합니다.\n"
        val crunchExplain:String ="\n"+
                "운동 설명 및 효과\n" +
                "스탠딩 사이드 크런치는 정면을 보고 서서 양 손을 뒤통수에 둔 뒤 무릎을 한 쪽씩 올려 팔꿈치에 붙히는 운동입니다.\n" +
                "옆구리 근육에 자극을 주어 슬림한 허리 라인을 만들어 주는 운동입니다.\n"
        val crunchWarning ="\n"+
                "1. 정면을 바라보고 운동을 시작합니다.\n" +
                "2. 왼쪽 발 부터 운동을 시작하고 자세가 틀리면 같은 방향의 발을 들어줘야 합니다.\n" +
                "3. 양 팔로 뒤통수를 잡아줍니다.\n" +
                "4. 다리를 최대한 올려 팔꿈치에 닿게 운동을 해줍니다.\n"
        val plankExplain:String ="\n"+
                "운동 설명 및 효과\n" +
                "허리나 기타 관절, 힘줄, 인대를 사용하지 않는 코어머슬 운동으로 땅과 몸만 있으면 어디서나 가능한 운동입니다.\n" +
                "엎드린 자세에서 널빤지처럼 평평한 상태로 수행하는 운동입니다.\n" +
                "몸에 코어 근력을 상승시켜 올바른 자세로 운동을 할 수 있도록 도와줍니다.\n"
        val plankWarning ="\n"+
                "1. 왼쪽을 바라보고 운동을 시작합니다.\n" +
                "2. 복부에 힘을 주고 온 몸이 직선이 되도록 유지를 해줍니다.\n" +
                "3. 고개를 숙이지 않고 정면을 바라봐주세요.\n" +
                "4. 팔의 각도가 90도가 되도록 지탱해줍니다.\n" +
                "5. 운동을 하다가 힘들다고 허리와 엉덩이를 움직이면 안됩니다."
        when(thisIntent.getStringExtra("typeOfExercise")){
            "squat" ->{
                exerciseName.text = "스쿼트"
                explain.text = squatExplain
                warningText.text = squatWarning
            }
            "lunge" -> {
                exerciseName.text = "런지"
                explain.text = lungeExplain
                warningText.text = lungeWarning
            }
            "standingCrunch" -> {
                exerciseName.text = "스탠딩 사이드 크런치"
                explain.text = crunchExplain
                warningText.text = crunchWarning
            }
            "plank" -> {
                exerciseName.text = "플랭크"
                explain.text = plankExplain
                warningText.text = plankWarning
            }
        }


        when(thisIntent.getStringExtra("typeOfExercise")){
            "squat" -> videoLink = "lnJPcQwRURY"
            "lunge" -> videoLink = "oYiBDWhmrX8"
            "standingCrunch" ->  videoLink = "nSzcY0NaXLM"
            "plank" -> videoLink = "Zq8nRY9P_cM"
        }
        tutorialVideo.play(videoLink)

        startButton.setOnClickListener {
            val myIntent = Intent(this, CameraActivity::class.java)
            myIntent.putExtra("typeOfExercise",thisIntent.getStringExtra("typeOfExercise"))
            Posestimation.init(this)
            AppStat.isExercising = true
            startActivity(myIntent)
            finish()

        }
    }

    override fun onResume() {
        super.onResume()
        Posestimation.init(this)
        if(AppStat.canResultDialog) {
            val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)

            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.exerciseresult, null)
            val okBtn = dialogView.findViewById<Button>(R.id.okButton)
            val curCal = dialogView.findViewById<TextView>(R.id.calorie)
            val exerciseTime = dialogView.findViewById<TextView>(R.id.exerciseTime)
            val todayCal = dialogView.findViewById<TextView>(R.id.todayCalorie)
            val todayTime = dialogView.findViewById<TextView>(R.id.todayTime)

            val cal = Calendar.getInstance()
            val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)


            val rQueue: RequestQueue = DB.makeNewRequestQueue(this)

            val data = HashMap<String, Any>()
            data["userID"] = AppStat.myStat.getUserId()
            data["totaltime"]=(AppStat.resExerciseTime).toInt()
            AppStat.myStat.addTodayTime(data["totaltime"].toString().toFloat())
            AppStat.myStat.addWeekTime(data["totaltime"].toString().toFloat())
            AppStat.myStat.addMonthTime(data["totaltime"].toString().toFloat())
            AppStat.myStat.addTotalTime(data["totaltime"].toString().toFloat())
            if(AppStat.myStat.getIsLogin()) {
                if (dayOfWeek == 1) {
                    AppStat.myStat.getWeekTimeList()[6].y += AppStat.resExerciseTime
                } else {
                    AppStat.myStat.getWeekTimeList()[dayOfWeek - 2].y += AppStat.resExerciseTime
                }
            }
            DB.postTimeRequest(this, rQueue, data)

            curCal.text = AppStat.curCal.toString()
            exerciseTime.text = AppStat.resExerciseTime.toString()
            todayCal.text = AppStat.myStat.getTodayCal().toString()
            todayTime.text = AppStat.myStat.getTodayTime().toString()

            builder.setView(dialogView)
            val alertDialog = builder.create()
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            okBtn.setOnClickListener {
                AppStat.canResultDialog = false
                alertDialog.dismiss()
            }

            alertDialog.show()
            alertDialog.window!!.setLayout((size.x * 0.7).toInt(), (size.y * 0.4).toInt())
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)


        startActivity(intent)
        finish()
    }
}