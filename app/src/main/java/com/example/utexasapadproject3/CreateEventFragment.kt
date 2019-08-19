package com.example.utexasapadproject3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject

class CreateEventFragment : Fragment(), HttpUtils, FragmentUtils {

    var clickable = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_event, container, false)

        doGetRequest("/api/venues", ::handleVenueResponse)

        val createEventButton = view.findViewById<View>(R.id.create_event_button)
        createEventButton.setOnClickListener {
            if (clickable) {
                val eventName = view.findViewById<TextView>(R.id.event_name_edit_text).text.toString()
                val eventDescription = view.findViewById<TextView>(R.id.event_description_edit_text).text.toString()
                val eventVenue = view.findViewById<Spinner>(R.id.venues_spinner).selectedItem.toString()
                val eventDateTime = view.findViewById<TextView>(R.id.event_datetime_edit_text).text.toString()
                val eventCapacity = view.findViewById<TextView>(R.id.event_capacity_edit_text).text.toString()

                val createEventRequest = CreateEventFragment.createEventInfo(
                    name = eventName,
                    description = eventDescription,
                    venue = eventVenue,
                    datetime = eventDateTime,
                    capacity = eventCapacity
                )
                val json = Gson().toJson(createEventRequest)
                doPostRequest("/api/events/create", json, ::handleCreateEvent)
            }
        }

        return view
    }

    fun handleVenueResponse(json: JSONObject, code: Int) {
        val handler = Handler(Looper.getMainLooper());
        handler.post({
            val jsonStr:String = json["data"].toString()
            val gson = GsonBuilder().create()
            val venueList = gson.fromJson(jsonStr,Array<Event>::class.java).toList()
            val venues: MutableList<String?> = mutableListOf()
            venueList.forEach {
                venues.add(it.name)
            }

            val spinner: Spinner = view!!.findViewById(R.id.venues_spinner)
            val adapter = ArrayAdapter<String>(this.context!!, android.R.layout.simple_spinner_item, venues)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.setAdapter(adapter)
        })
    }

    fun handleCreateEvent(json: JSONObject, code: Int) {
        val handler = Handler(Looper.getMainLooper());

        handler.post({
            if (code == 200) {
                Toast.makeText(this.context, "Event Created Successfully", Toast.LENGTH_SHORT).show()
                navigateTo(DashboardFragment(), fragmentManager)
            } else {
                Toast.makeText(this.context, "Create Event Failed", Toast.LENGTH_SHORT).show()
                val jsonStr:String = json["msg"].toString()
                println(jsonStr)
            }
        })
        resume()
    }

    private data class createEventInfo(
        val name: String,
        val description: String,
        val venue: String,
        val datetime: String,
        val capacity: String
    )

    fun resume() {
        clickable = true
    }
}