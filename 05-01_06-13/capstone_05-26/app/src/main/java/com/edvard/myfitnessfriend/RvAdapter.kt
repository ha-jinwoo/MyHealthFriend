package com.edvard.myfitnessfriend

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RvAdapter (val context:Context, val friendList: ArrayList<UserInfo>): RecyclerView.Adapter<RvAdapter.Holder>(){

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
        val todayCal = itemView.findViewById<TextView>(R.id.todayCal)
        val statisticsBtn = itemView.findViewById<Button>(R.id.statisticButton)



        fun bind(info: UserInfo, context: Context){
            friendId.text = info.getUserId()
            todayCal.text = info.getTodayCal().toString()
            statisticsBtn.setOnClickListener {
                val mintent = Intent(context,FriendStatisticsActivity::class.java)
                context.startActivity(mintent)
            }
        }
    }
}