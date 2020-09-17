package com.edvard.myfitnessfriend

import android.content.Context
import java.util.ArrayList
import android.graphics.PointF
import android.media.MediaPlayer
import android.os.Handler
import android.widget.Toast
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.sqrt

class Posestimation {

    companion object{
        /*현재운동*/
        var curExercise: String = "Nothing"
        /*각도들*/
        var LHKAAngle: Double = 0.0 //무릎각도
        var RHKAAngle: Double = 0.0
        var LNHKAngle: Double = 0.0 // 목 엉덩이 무릎 각도
        var RNHKAngle: Double = 0.0
        /*Flags*/
        var isFirst: Boolean = true
        var isStand: Boolean = false
        var isSquart: Boolean = false
        var rightLunge: Boolean = false
        var cntFlag: Boolean = false
        var wrongPoseFlag: Boolean = false
            /*Lunge Flags*/
        var wrongKneeError_lunge : Boolean = false
        var wrong90Error_lunge : Boolean = false
        var wrongWaistError_lunge : Boolean = false
        var exist90 : Boolean = false

        var isPlaying : Boolean = false
        /*길이들*/
        var LKADist: Double = 0.0//왼쪽 무릎 발목
        var RKADist: Double = 0.0//오른쪽 무릎 발목
        var LNHDist: Double = 0.0//왼쪽 목 엉덩이
        var RNHDist: Double = 0.0//오른쪽 목 엉덩이
        /*운동 개수*/
        var exerciseCnt: Int = 0


        var sound : Int = 0

        private fun calcAngle(p1 : PointF, p2 :PointF, p3 : PointF) : Double{
            val a = (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y)
            val b = (p2.x - p3.x) * (p2.x - p3.x) + (p2.y - p3.y) * (p2.y - p3.y)
            val c = (p3.x - p1.x) * (p3.x - p1.x) + (p3.y - p1.y) * (p3.y - p1.y)
            return acos( ((a+b-c) / sqrt( (4 * a * b)  )) ) * 180/3.141592
        }
        private fun calcDist(p1: PointF, p2: PointF) : Double {
            return sqrt(((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)).toDouble())
        }

        private fun Play_Sound(context: Context, sound:Int){
            var Sound: MediaPlayer = MediaPlayer.create(context, sound)
            var delaySec : Long = 2310
            if(!isPlaying) {
                Sound?.start()
                isPlaying = true
                if(sound == R.raw.one
                        || sound == R.raw.two
                        || sound == R.raw.three
                        || sound == R.raw.four
                        || sound == R.raw.five
                        || sound == R.raw.six
                        || sound == R.raw.seven
                        || sound == R.raw.eight
                        || sound == R.raw.nine
                        || sound == R.raw.ten)
                    delaySec = 1000
                else if ( sound == R.raw.lunge_90_error
                        || sound == R.raw.lunge_waist_error
                        || sound == R.raw.lunge_knee_error
                        || sound == R.raw.squat_waist_error
                        || sound == R.raw.squat_knee_error) {
                    delaySec = 1500
                }
                else if ( sound == R.raw.lunge_all_error){
                    delaySec = 2451
                }
                Handler().postDelayed({
                    Sound?.release()
                    isPlaying = false
                }, delaySec)
            }
        }
        /*스쿼트 판별*/
        fun Squat(context: Context, mDrawPoint : ArrayList<PointF> ) {
            //L엉덩이, L무릎 L발목을 이용하여 무릎 각도를 계산
            LHKAAngle = calcAngle(mDrawPoint[8], mDrawPoint[9], mDrawPoint[10])
            //R엉덩이, R무릎 R발목을 이용하여 무릎 각도를 계산
            RHKAAngle = calcAngle(mDrawPoint[11], mDrawPoint[12], mDrawPoint[13])

            //서있는자세는 좌우 엉덩이 무릎 발목의 각도를 계산해서 이 각도가 170도에서 180도 사이이면
            //서있는자세 아니면 서있지 않는 자세로 판별한다.
            if( (170.toDouble() <= LHKAAngle && LHKAAngle <= 180.toDouble()) &&
                    (170.toDouble() <= RHKAAngle && RHKAAngle <= 180.toDouble())
            ) {//stand
                isStand = true
                //자세 판별을 위해 사용자의 몸이 들어올 경우 한 번만 정보를 가져옴
                if(isFirst){
                    LKADist = calcDist( mDrawPoint[9], mDrawPoint[10])
                    RKADist = calcDist( mDrawPoint[12], mDrawPoint[13])
                    LNHDist = calcDist( mDrawPoint[1], mDrawPoint[8])
                    RNHDist = calcDist( mDrawPoint[1], mDrawPoint[11])
                }

                //올바른 자세인 경우 운동 횟수를 증가 시켜줌
                if(cntFlag && !wrongPoseFlag){
                    cntFlag = false
                    Play_Sound(context, selectCountSound(exerciseCnt))
                    exerciseCnt++
                    println("Squart Count : $exerciseCnt")
                }
                //flag 초기화
                wrongPoseFlag = false
                isSquart = false
                cntFlag = false
                println("You are standing")
            }
            //좌우 엉덩이 - 무릎 - 발목 각도 (무릎의 굽혀진정도) 가 120도 이하이면 스쿼트 자세라고 판별을한다.
            else if ( LHKAAngle <= 120.toDouble() && RHKAAngle <= 120.toDouble()) {//squart
                isStand = false
                //현재 자세의 무릎-발목 길이가 서있는 자세의 무릎 - 발목 길이의 70퍼센트 이하이면 무릎이 앞으로 나왔다고 판별한다.
                //현재 자세의 목-엉덩이 길이가 서있던 자세의 목-엉덩이의 길이의 80퍼센트 이하이면 허리가 앞으로 나간것으로 판별한다.
                if((calcDist( mDrawPoint[9], mDrawPoint[10]) <= LKADist * 0.7 || calcDist( mDrawPoint[12], mDrawPoint[13]) <= RKADist * 0.7)
                        && (calcDist( mDrawPoint[1], mDrawPoint[8]) <= LNHDist *0.8 && calcDist( mDrawPoint[1], mDrawPoint[11]) < RNHDist*0.8 )
                        && !isSquart){
                    //맨 처음엔 두 경우가 다 잘못된 경우를 판별하여 둘다 잘못 된 경우에 무릎을 뒤로 빼고 허리를 피라는 음성 피드백을 출력한다.
                    isSquart = false
                    wrongPoseFlag = true
                    Play_Sound(context, R.raw.squat_knee_waist_error)
                }
                else if (calcDist( mDrawPoint[9], mDrawPoint[10]) <= LKADist * 0.7 || calcDist( mDrawPoint[12], mDrawPoint[13]) <= RKADist * 0.7 && !isSquart){
                    //무릎만 앞으로 나왔을 경우 무릎을 뒤로 빼라는 음성 피드백을 출력한다.
                    isSquart = false
                    wrongPoseFlag = true
                    Play_Sound(context, R.raw.squat_knee_error)
                    println("Wrong Squart Pose")
                }
                else if(calcDist( mDrawPoint[1], mDrawPoint[8]) <= LNHDist *0.8 && calcDist( mDrawPoint[1], mDrawPoint[11]) < RNHDist*0.8 && !isSquart){
                    //허리만 앞으로 나왔을 경우 허리를 피라는 음성 피드백을 출력한다.
                    isSquart = false
                    wrongPoseFlag = true
                    Play_Sound(context, R.raw.squat_waist_error)
                }
                else {
                    //위에 있는 잘못 된 조건에 해당되지 않을 경우 올바른 자세로 인식한다.
                    if(!wrongPoseFlag) {
                        isSquart = true
                        cntFlag = true
                    }
                }

            }
        }
        /*런지 판별*/
        fun Lunge(context: Context,mDrawPoint: ArrayList<PointF>){
            //Toast.makeText(context, "This is Lunge()!!",Toast.LENGTH_SHORT).show()
            //왼쪽을 보고 서줘야 한다!
            LHKAAngle = calcAngle(mDrawPoint[8], mDrawPoint[9], mDrawPoint[10])//왼쪽무릎 각도
            RHKAAngle = calcAngle(mDrawPoint[11], mDrawPoint[12], mDrawPoint[13])//오른쪽 무릎 각도
            LNHKAngle = calcAngle(mDrawPoint[1], mDrawPoint[8], mDrawPoint[9])//허리각도
            RNHKAngle = calcAngle(mDrawPoint[1], mDrawPoint[11], mDrawPoint[12])//허리각도
            var LAnkleLoc = mDrawPoint[10].x
            var RAnkleLoc = mDrawPoint[13].x
            if( abs(RAnkleLoc - LAnkleLoc) <= 80 &&
                    abs(mDrawPoint[9].x - mDrawPoint[12].x) <= 80){
                //stand
                println("Your Standing!!")
                if(rightLunge){
                    if(wrongWaistError_lunge){
                        Play_Sound(context, R.raw.lunge_waist_error)
                    }
                    else {
                        Play_Sound(context, selectCountSound(exerciseCnt))
                        if(cntFlag){
                            cntFlag = false
                            exerciseCnt++
                        }
                    }
                    cntFlag = false
                    rightLunge = false
                    wrongWaistError_lunge = false
                    wrong90Error_lunge = false
                    wrongKneeError_lunge = false
                    exist90 = false
                }
                else if(wrongKneeError_lunge || wrongWaistError_lunge || wrong90Error_lunge){
                    if(wrongKneeError_lunge && wrongWaistError_lunge && wrong90Error_lunge && !exist90){
                        Play_Sound(context, R.raw.lunge_all_error)
                    }
                    else if(wrong90Error_lunge && wrongWaistError_lunge && !exist90){
                        Play_Sound(context, R.raw.lunge_90_waist_error)
                    }
                    else if(wrong90Error_lunge && wrongKneeError_lunge && !exist90){
                        Play_Sound(context, R.raw.lunge_90_knee_error)
                    }
                    else if(wrongKneeError_lunge && wrongWaistError_lunge){
                        Play_Sound(context, R.raw.lunge_knee_waist_error)
                    }
                    else if(wrongWaistError_lunge){
                        Play_Sound(context, R.raw.squat_waist_error)
                    }
                    else if(wrongKneeError_lunge){
                        Play_Sound(context, R.raw.lunge_knee_error)
                    }
                    else if(wrong90Error_lunge && !exist90){
                        Play_Sound(context, R.raw.lunge_90_error)
                    }
                    wrongWaistError_lunge = false
                    wrong90Error_lunge = false
                    wrongKneeError_lunge = false
                    exist90 = false
                    rightLunge = false
                    cntFlag = false
                }
            }
            else if(LAnkleLoc < RAnkleLoc ){
                //왼발을 앞으로 내민 경우
                if(RAnkleLoc - LAnkleLoc >= 300 && !rightLunge){
                    //런지 시작
                    if(mDrawPoint[9].x < LAnkleLoc - 50){
                        //무릎이 발목보다 앞으로 나온경우
                        wrongKneeError_lunge = true
                    }
                    else if(mDrawPoint[1].x + 65 < mDrawPoint[8].x){
                        //허리가 잘못됬을떄
                        wrongWaistError_lunge = true
                    }
                    else if(LHKAAngle > 100.toDouble() ){
                        //무릎을 너무 조금 굽혔을 경우
                        wrong90Error_lunge = true
                    }
                    else{
                        //올바른 자세
                        rightLunge = true
                        exist90 = false
                        cntFlag = true
                    }
                }
                if(LHKAAngle <= 100.toDouble() && !exist90){
                    exist90 = true
                }
            }
            else{
                //오른발을 앞으로 내민 경우
                if(LAnkleLoc - RAnkleLoc >= 300 && !rightLunge){
                    //런지 시작
                    if(mDrawPoint[12].x < RAnkleLoc - 50){
                        //무릎이 발목보다 앞으로 나온경우
                        wrongKneeError_lunge = true
                    }
                    else if(mDrawPoint[1].x + 65 < mDrawPoint[11].x){
                        //허리가 잘못됬을떄
                        wrongWaistError_lunge = true
                    }
                    else if(RHKAAngle > 100.toDouble() ){
                        //무릎을 너무 조금 굽혔을 경우
                        wrong90Error_lunge = true
                    }
                    else{
                        //올바른 자세
                        rightLunge = true
                        exist90 = false
                        cntFlag = true
                    }
                }
                if(RHKAAngle <= 100.toDouble() && !exist90){
                    exist90 = true
                }
            }

        }
        fun StandingCrunch(context: Context, mDrawPoint: ArrayList<PointF>){
            var a = mDrawPoint
            var mcontext = context
            /*
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            * */
        }
        fun init(){
            exerciseCnt = 0
            LKADist = 0.0
            RKADist = 0.0
            LNHDist = 0.0
            RNHDist = 0.0
            isFirst = true
            isStand = false
            isSquart= false
            rightLunge = false
            cntFlag= false
            wrongPoseFlag = false
            wrongKneeError_lunge  = false
            wrong90Error_lunge  = false
            wrongWaistError_lunge  = false
            exist90  = false

        }
        private fun selectCountSound(v: Int): Int{
            var cntSound: Int = R.raw.one
            when(v){
                0 -> cntSound = R.raw.one
                1 -> cntSound = R.raw.two
                2 -> cntSound = R.raw.three
                3 -> cntSound = R.raw.four
                4 -> cntSound = R.raw.five
                5 -> cntSound = R.raw.six
                6 -> cntSound = R.raw.seven
                7 -> cntSound = R.raw.eight
                8 -> cntSound = R.raw.nine
                9 -> cntSound = R.raw.ten
            }
            return  cntSound
        }
    }
}