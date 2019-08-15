package com.example.utexasapadproject3


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val loginButton = view.findViewById<View>(R.id.lf_login_button)
        loginButton.setOnClickListener({

            val fragManager = fragmentManager
            if (fragManager != null) {
                val transaction = fragManager
                    .beginTransaction()
                    .replace(R.id.container, DashboardFragment())
                    .addToBackStack("dashboard_fragment")

                Toast.makeText(this.context, "Login Successful", Toast.LENGTH_SHORT).show()
                transaction.commit()
            }
        })

        return view
    }


}
