package com.example.utexasapadproject3


import android.annotation.TargetApi
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class EventDetailsFragment : Fragment(), HttpUtils, FragmentUtils {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_details, container, false)

        val bundle = this.arguments
        val eventId = bundle?.getString("eventId")

        val joinEventButton = view.findViewById<View>(R.id.join_event_button)
        val leaveEventButton = view.findViewById<View>(R.id.leave_event_button)

        joinEventButton.setOnClickListener({
            val currentUser = JSONObject(Global.getUser())
            val userId = currentUser["id"].toString()

            val joinEventRequest = UserEvent(
                userId = userId,
                eventId = eventId.toString()
            )
            val json = Gson().toJson(joinEventRequest)
            doPostRequest("/api/events/join", json, ::handleEventActions)
        })

        leaveEventButton.setOnClickListener({
            val currentUser = JSONObject(Global.getUser())
            val userId = currentUser["id"].toString()

            val leaveEventRequest = UserEvent(
                userId = userId,
                eventId = eventId.toString()
            )
            val json = Gson().toJson(leaveEventRequest)
            doPostRequest("/api/events/leave", json, ::handleEventActions)
        })

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
                val datetimeParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val datetimeFormatter = SimpleDateFormat("MMMM dd, yyyy | h:mm a")
                val datetime = datetimeFormatter.format(datetimeParser.parse(event.getString("datetime"))!!)
                eventDate?.text = datetime

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

                // handle buttons
                val joinEventButton = view?.findViewById<Button>(R.id.join_event_button)
                val leaveEventButton = view?.findViewById<Button>(R.id.leave_event_button)
                joinEventButton?.visibility = View.GONE
                leaveEventButton?.visibility = View.GONE

                val currentUser = JSONObject(Global.getUser())
                val username = currentUser["username"].toString()

                var foundUser = false
                for (i in 0 until particpants.length()) {
                    if (particpants[i].toString() == username) foundUser = true
                }

                if (foundUser) {
                    leaveEventButton?.visibility = View.VISIBLE
                } else {
                    joinEventButton?.visibility = View.VISIBLE
                }
            }
        })
    }

    fun handleEventActions(json: JSONObject, code: Int) {
        val handler = Handler(Looper.getMainLooper());
        handler.post({
            if (code == 200) {
                Toast.makeText(this.context, json["msg"].toString(), Toast.LENGTH_SHORT).show()

                val eventId = this.arguments?.getString("eventId")
                val bundle = Bundle()
                bundle.putString("eventId", eventId)
                val fragment = EventDetailsFragment()
                fragment.setArguments(bundle)
                // reload fragments
                reload(this, fragmentManager)
            }
            else {
                Toast.makeText(this.context, json["warning"].toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private data class UserEvent(
        val userId: String,
        val eventId: String
    )
}

