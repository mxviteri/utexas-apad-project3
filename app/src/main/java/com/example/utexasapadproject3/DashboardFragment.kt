package com.example.utexasapadproject3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.appbar.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import org.json.JSONObject

class DashboardFragment : Fragment(), HttpUtils, FragmentUtils {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        doGetRequest("/api/events", ::handleEvents)

        (activity as AppCompatActivity).setSupportActionBar(view.app_bar)

        return view
    }

    fun handleEvents(json: JSONObject, code: Int) {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.appbar_menu, menu)


        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search Events")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                val fragment = SearchResultFragment()
                val bundle = Bundle()
                bundle.putString("searchQuery", query)
                fragment.setArguments(bundle)

                navigateTo(fragment, fragmentManager)
                return false
            }

        })
    }
}
