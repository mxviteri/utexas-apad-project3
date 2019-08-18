package com.example.utexasapadproject3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.util.*
import android.view.MenuItem


class MainActivity : AppCompatActivity(), FragmentUtils {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, LoginFragment())
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.my_events -> {
                navigateTo(MyEventsFragment(), supportFragmentManager)
                return true
            }
            R.id.logout -> {
                Global.setUser({}.toString())
                navigateTo(LoginFragment(), supportFragmentManager)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
