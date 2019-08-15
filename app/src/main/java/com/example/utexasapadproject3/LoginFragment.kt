package com.example.utexasapadproject3


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import org.json.JSONObject


class LoginFragment : Fragment(), HttpUtils, FragmentUtils {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton = view.findViewById<View>(R.id.login_button)
        loginButton.setOnClickListener({
            val userTextField = view.findViewById<TextView>(R.id.username_edit_text)
            val passTextField = view.findViewById<TextView>(R.id.password_edit_text)

            val loginRequest = LoginInfo(
                username = userTextField.text.toString(),
                password = passTextField.text.toString()
            )
            val json = Gson().toJson(loginRequest)
            doPostRequest("/api/users/login", json, ::handleLogin)
        })

        return view
    }

    fun handleLogin(json: JSONObject) {
        val handler = Handler(Looper.getMainLooper());

        println("THE JSON " + json)
        if (json["msg"] == "User logged in successfully") {
            handler.post({
                Toast.makeText(this.context, "Login Successful", Toast.LENGTH_SHORT).show()
                navigateTo(DashboardFragment(), fragmentManager)
            })
        } else {
            handler.post({
                Toast.makeText(this.context, "Login Failed", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private data class LoginInfo(
        val username: String,
        val password: String
    )
}