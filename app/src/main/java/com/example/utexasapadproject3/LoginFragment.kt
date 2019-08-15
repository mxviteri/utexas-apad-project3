package com.example.utexasapadproject3


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import android.R.string
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class LoginFragment : Fragment(), HttpUtils, FragmentUtils {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton = view.findViewById<View>(R.id.lf_login_button)
        loginButton.setOnClickListener({
            val userTextField = view.findViewById<TextView>(R.id.lf_username)
            val passTextField = view.findViewById<TextView>(R.id.lf_password)
            userTextField.text = ""
            passTextField.text = ""

            Toast.makeText(this.context, "Login Successful", Toast.LENGTH_SHORT).show()
            navigateTo(DashboardFragment(), fragmentManager)
        })

        return view
    }

}
