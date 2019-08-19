package com.example.utexasapadproject3

import android.annotation.TargetApi
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


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

    @TargetApi(16)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.my_events -> {
                navigateTo(MyEventsFragment(), supportFragmentManager)
                return true
            }
            R.id.logout -> {
                Global.setUser({}.toString())
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                this.startActivity(intent)
                return true
            }
            R.id.create_event -> {
                navigateTo(CreateEventFragment(), supportFragmentManager)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
