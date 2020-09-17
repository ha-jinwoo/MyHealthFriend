package com.edvard.myfitnessfriend.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.edvard.myfitnessfriend.DB
import com.edvard.myfitnessfriend.R
import com.edvard.myfitnessfriend.User
import com.edvard.myfitnessfriend.activities.FriendStatisticsActivity

class RvAdapter (val context:Context, val friendList: ArrayList<User>): RecyclerView.Adapter<RvAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.firend_element, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(friendList[position], context)
    }
    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val friendId = itemView.findViewById<TextView>(R.id.friendID)
        val todayCal = itemView.findViewById<TextView>(R.id.date)
        val statisticsBtn = itemView.findViewById<Button>(R.id.statisticButton)



        fun bind(info: User, context: Context){
            friendId.text = info.getUserId()
            val userID=info.getUserId()
            val data = HashMap<String, Any>()
            data["userID"] = userID
            data["calorie"] = 0
            val queue: RequestQueue = DB.makeNewRequestQueue(context)
            queue.addRequestFinishedListener<String> {
                todayCal.text = data["calorie"].toString()
            }
            DB.friendCalorieRequest(context, queue, data)

            statisticsBtn.setOnClickListener {
                val mintent = Intent(context, FriendStatisticsActivity::class.java)
                val queue2 = DB.makeNewRequestQueue(context)
                val data2 = HashMap<String, Any>()
                data2["userID"] = userID
                queue2.addRequestFinishedListener<String> {
                    context.startActivity(mintent)
                }
                DB.friendWeekCalorieRequest(context, queue2, data2)
            }
        }
    }
}