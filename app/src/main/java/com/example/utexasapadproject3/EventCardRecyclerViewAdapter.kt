package com.example.utexasapadproject3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class EventCardRecyclerViewAdapter(private val eventList: List<Event>) : RecyclerView.Adapter<EventCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventCardViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)
        return EventCardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: EventCardViewHolder, position: Int) {
        if (position < eventList.size) {
            val event = eventList[position]
            holder.eventName.text = event.name?.capitalize()
            holder.eventVenue.text = event.venue?.capitalize()
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}