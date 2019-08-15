package com.example.utexasapadproject3

import android.app.PendingIntent.getActivity
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface FragmentUtils {

    fun navigateTo(fragment: Fragment, fragmentManager: FragmentManager?) {

        if (fragmentManager != null) {
            fragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }


}