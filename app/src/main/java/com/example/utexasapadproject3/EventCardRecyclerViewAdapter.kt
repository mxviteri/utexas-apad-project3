package com.example.utexasapadproject3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
            val dateParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val dateFormatter = SimpleDateFormat("MMMM dd, yyyy")
            val date = dateFormatter.format(dateParser.parse(event.datetime)!!)
            val timeParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val timeFormatter = SimpleDateFormat("h:mm a")
            val time = timeFormatter.format(timeParser.parse(event.datetime)!!)
            holder.eventDate.text = date
            holder.eventTime.text = time
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}