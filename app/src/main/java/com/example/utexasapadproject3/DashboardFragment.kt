package com.example.utexasapadproject3


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.json.JSONObject

class DashboardFragment : Fragment(), HttpUtils {
    private var dataView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        dataView = view.findViewById(R.id.dashboard_text)

        doGetRequest("/api/events", ::handleEvents)
        return view
    }

    fun handleEvents(json: JSONObject) {
        val handler = Handler(Looper.getMainLooper());
        handler.post({
            dataView?.text = json.toString()
        })
    }
}
