package com.example.padelbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.padelbook.R
import com.example.padelbook.models.Match

class MatchAdapter(private val matches : List<Match>) :
    RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {


    class MatchViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_activity, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.view.findViewById<TextView>(R.id.p1_text_view).text = match.p1.value
        holder.view.findViewById<TextView>(R.id.p2_text_view).text = match.p2.value
        holder.view.findViewById<TextView>(R.id.p3_text_view).text = match.p3.value
        holder.view.findViewById<TextView>(R.id.p4_text_view).text = match.p4.value

        holder.view.findViewById<TextView>(R.id.location_text_view).text = match.location.value
        holder.view.findViewById<TextView>(R.id.time_text_view).text = match.time.value
    }

    override fun getItemCount() = matches.size



}