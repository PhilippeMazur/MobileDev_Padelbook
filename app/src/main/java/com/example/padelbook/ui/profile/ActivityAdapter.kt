package com.example.padelbook.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.padelbook.R
import com.example.padelbook.models.Match

class ActivityAdapter(private val items : List<Match>) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.date)
        var location: TextView = itemView.findViewById(R.id.location)

        var player1: TextView = itemView.findViewById(R.id.player1)
        var player2: TextView = itemView.findViewById(R.id.player2)
        var player3: TextView = itemView.findViewById(R.id.player3)
        var player4: TextView = itemView.findViewById(R.id.player4)

        var time: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = items[position]
        holder.date.text = match.date.value
        holder.location.text = match.location.value

        holder.player1.text = match.p1.value
        holder.player2.text = match.p2.value
        holder.player3.text = match.p3.value
        holder.player4.text = match.p4.value


        holder.time.text = match.time.value
    }

    override fun getItemCount(): Int {
        return items.size
    }

}