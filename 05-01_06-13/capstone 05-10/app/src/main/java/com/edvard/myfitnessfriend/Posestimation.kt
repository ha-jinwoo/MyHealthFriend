package com.edvard.myfitnessfriend

import android.content.Context
import java.util.ArrayList
import android.graphics.PointF
import android.media.MediaPlayer
import android.os.Handler
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.sqrt

class Posestimation {

    companion object{
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
        var isNothingPose: Boolean = false
        var cntFlag: Boolean = false
        var wrongPoseFlag: Boolean = false
            /*Lunge Flags*/
        var wrongKneeError_lunge : Boolean = false
        var wrong90Error_lunge : Boolean = false
        var wrongWaistError_lunge : Boolean = false
        var exist90 : Boolean = false

        var isPlaying : Boolean = false
        /*길이들*/
        var LKADist: Double = 0.0
        var RKADist: Double = 0.0
        var LNHDist: Double = 0.0
        var RNHDist: Double = 0.0
        var squartCnt: Int = 0


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
            var delaySec : Long = 2500
            if(!isPlaying) {
                Sound?.start()
                isPlaying = true
                if (sound == R.raw.one
                        || sound == R.raw.lunge_90_error
                        || sound == R.raw.lunge_waist_error
                        || sound == R.raw.lunge_knee_error
                        || sound == R.raw.squat_waist_error
                        || sound == R.raw.squat_knee_error) {
                    delaySec = 1500
                }
                Handler().postDelayed({
                    Sound?.release()
                    isPlaying = false
                }, delaySec)
            }
        }
        fun Squat(context: Context, mDrawPoint : ArrayList<PointF> ) {
            //setLHKAAngle()
            LHKAAngle = calcAngle(mDrawPoint[8], mDrawPoint[9], mDrawPoint[10])
            //setRHKAAngle()
            RHKAAngle = calcAngle(mDrawPoint[11], mDrawPoint[12], mDrawPoint[13])
            if( (170.toDouble() <= LHKAAngle && LHKAAngle <= 180.toDouble()) &&
                    (170.toDouble() <= RHKAAngle && RHKAAngle <= 180.toDouble())
            ) {//stand
                isStand = true
                if(isFirst){
                    LKADist = calcDist( mDrawPoint[9], mDrawPoint[10])
                    RKADist = calcDist( mDrawPoint[12], mDrawPoint[13])
                    LNHDist = calcDist( mDrawPoint[1], mDrawPoint[5])
                    RNHDist = calcDist( mDrawPoint[1], mDrawPoint[8])
                }

                if(cntFlag && !wrongPoseFlag){
                    squartCnt++
                    cntFlag = false
                    wrongPoseFlag = false
                    Play_Sound(context, R.raw.one)
                    println("Squart Count : $squartCnt")
                }
                println("You are standing")
            }
            else if ( LHKAAngle <= 120.toDouble() && RHKAAngle <= 120.toDouble()) {//squart
                if((calcDist( mDrawPoint[9], mDrawPoint[10]) <= LKADist * 0.5 || calcDist( mDrawPoint[12], mDrawPoint[13]) <= RKADist * 0.5)
                        && (calcAngle(mDrawPoint[1],mDrawPoint[5],mDrawPoint[6]) <= 105.toDouble() || calcAngle(mDrawPoint[1], mDrawPoint[8], mDrawPoint[9]) <= 105.toDouble() ) ){
                    //무릎이 앞으로 나왔을때, 허리가 앞으로 나왔을떄
                    isSquart = false
                    wrongPoseFlag = true
                    isNothingPose = false
                    Play_Sound(context, R.raw.squat_knee_error)
                }
                else if (calcDist( mDrawPoint[9], mDrawPoint[10]) <= LKADist * 0.5 || calcDist( mDrawPoint[12], mDrawPoint[13]) <= RKADist * 0.5){
                    //무릎만 앞으로 나왔을때
                    isSquart = false
                    wrongPoseFlag = true
                    isNothingPose = false
                    Play_Sound(context, R.raw.squat_knee_error)
                    println("Wrong Squart Pose")
                }
                else if(calcAngle(mDrawPoint[1],mDrawPoint[5],mDrawPoint[6]) <= 105.toDouble() || calcAngle(mDrawPoint[1], mDrawPoint[8], mDrawPoint[9]) <= 105.toDouble() ){
                    // 허리만 앞으로 나왔을때
                    isSquart = false
                    wrongPoseFlag = true
                    isNothingPose = false
                    Play_Sound(context, R.raw.squat_waist_error)
                }
                else {
                    //정자세
                    isSquart = true
                    cntFlag = true
                }
                println("You are squarting")

            }
            else{
                isSquart = false
                wrongPoseFlag = false
                isNothingPose = true
                println("Nothing")
            }
        }
        fun Lunge(context: Context,mDrawPoint: ArrayList<PointF>){
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
                    else Play_Sound(context, R.raw.one)
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
                }
            }
            else if(LAnkleLoc < RAnkleLoc ){
                //왼발을 앞으로 내민 경우
                if(RAnkleLoc - LAnkleLoc >= 300 && !rightLunge){
                    //런지 시작
                    if(mDrawPoint[9].x < LAnkleLoc - 40){
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
                    if(mDrawPoint[12].x < RAnkleLoc - 40){
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
                    }
                }
                if(RHKAAngle <= 100.toDouble() && !exist90){
                    exist90 = true
                }
            }

        }
    }
}