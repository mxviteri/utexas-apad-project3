package com.example.utexasapadproject3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import org.json.JSONObject

class SearchResultFragment : Fragment(), HttpUtils {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_result, container, false)

        val bundle = this.arguments
        val searchQuery = bundle?.getString("searchQuery")

        doGetRequest("/api/events?search=$searchQuery", ::handleResponse)

        return view
    }

    fun handleResponse(json: JSONObject, code: Int) {
        val handler = Handler(Looper.getMainLooper());
        handler.post({
            val jsonStr:String = json["data"].toString()
            val gson = GsonBuilder().create()
            val eventList = gson.fromJson(jsonStr,Array<Event>::class.java).toList()

            view?.recycler_view?.setHasFixedSize(true)
            view?.recycler_view?.layoutManager = GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
            val adapter = EventCardRecyclerViewAdapter(eventList)
            view?.recycler_view?.adapter = adapter
        })
    }

}