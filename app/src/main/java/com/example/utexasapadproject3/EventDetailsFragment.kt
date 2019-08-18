package com.example.utexasapadproject3


import android.annotation.TargetApi
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class EventDetailsFragment : Fragment(), HttpUtils {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_details, container, false)

        val bundle = this.arguments
        val eventId = bundle?.getString("eventId")

        doGetRequest("/api/events/" + eventId, ::handleEvent)
        doGetRequest("/api/events/" + eventId + "/participants", ::handleEventParticipants)

        return view
    }

    fun handleEvent(json: JSONObject, code: Int) {
        val handler = Handler(Looper.getMainLooper());

        handler.post({
            if (code == 200) {
                val event = json.getJSONObject("data")

                val eventName = view?.findViewById<TextView>(R.id.ed_name)
                eventName?.text = event.getString("name").capitalize()

                val eventCapacity = view?.findViewById<TextView>(R.id.ed_capacity)
                eventCapacity?.text = event.getString("capacity") + " spots"

                val eventDate = view?.findViewById<TextView>(R.id.ed_datetime)
                val date = event.getString("datetime")
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
                eventDate?.text = simpleDateFormat.parse(date).toString()

                val eventDescription = view?.findViewById<TextView>(R.id.ed_description)
                eventDescription?.text = event.getString("description")

                val usersTitle = view?.findViewById<TextView>(R.id.ed_users_title)
                usersTitle?.text = "Users in this event:"
            } else {
                Toast.makeText(this.context, "Failed to fetch event info", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun handleEventParticipants(json: JSONObject, code: Int) {
        val handler = Handler(Looper.getMainLooper());
        handler.post({
            if (code == 200) {
                val particpants = json.getJSONArray("participants")

                val users = view?.findViewById<TextView>(R.id.ed_users)
                users?.text = ""

                for (i in 0 until particpants.length()) {
                    users?.append(particpants[i].toString() + "\n")
                }
            }
        })
    }
}

