package com.example.utexasapadproject3

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var eventName: TextView = itemView.findViewById(R.id.event_name)
    var eventVenue: TextView = itemView.findViewById(R.id.event_venue)

}