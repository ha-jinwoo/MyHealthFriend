package com.edvard.myfitnessfriend

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.data.Entry
import org.json.JSONException
import org.json.JSONObject

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
        val todayCal = itemView.findViewById<TextView>(R.id.todayCal)
        val statisticsBtn = itemView.findViewById<Button>(R.id.statisticButton)



        fun bind(info: User, context: Context){
            friendId.text = info.getUserId()
            var calorie : String = "0"
            val userID=info.getUserId()
            val responseListener2 = Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        calorie = jsonObject.getString("userCALORIE")
                        Toast.makeText(context, calorie, Toast.LENGTH_SHORT).show()
                    } else {
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            val queue: RequestQueue = Volley.newRequestQueue(context)
            val getFriendCalorieRequest = GetFriendCalorieRequest(userID, responseListener2)
            queue.add(getFriendCalorieRequest)

            Handler().postDelayed({
                todayCal.text = calorie
            },500)

            statisticsBtn.setOnClickListener {
                val mintent = Intent(context,FriendStatisticsActivity::class.java)

                val responseListener = Response.Listener<String?> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success) {
                            val one = jsonObject.getString("one").toFloat()
                            val two = jsonObject.getString("two").toFloat()
                            val three = jsonObject.getString("three").toFloat()
                            val four = jsonObject.getString("four").toFloat()
                            val five = jsonObject.getString("five").toFloat()
                            val six = jsonObject.getString("six").toFloat()
                            val seven = jsonObject.getString("seven").toFloat()

                            var values = ArrayList<Entry>()
                            values.add(Entry(1.toFloat(),one))
                            values.add(Entry(2.toFloat(),two))
                            values.add(Entry(3.toFloat(),three))
                            values.add(Entry(4.toFloat(),four))
                            values.add(Entry(5.toFloat(),five))
                            values.add(Entry(6.toFloat(),six))
                            values.add(Entry(7.toFloat(),seven))
                            AppStat.myStat.setFriendWeekCalorie(values)

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                val queue: RequestQueue = Volley.newRequestQueue(context)
                val getWeekCalorieRequest = GetWeekCalorieRequest(userID, responseListener)
                queue.add(getWeekCalorieRequest)

                Handler().postDelayed({
                    context.startActivity(mintent)
                },500)

            }
        }
    }
}