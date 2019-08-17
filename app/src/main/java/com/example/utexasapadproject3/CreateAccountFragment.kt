package com.example.utexasapadproject3


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import org.json.JSONObject


class CreateAccountFragment : Fragment(), HttpUtils, FragmentUtils {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_account, container, false)

        val createAccountButton = view.findViewById<View>(R.id.create_account_button)
        createAccountButton.setOnClickListener({
            val userTextField = view.findViewById<TextView>(R.id.create_username)
            val passTextField = view.findViewById<TextView>(R.id.create_password)

            val createAccountRequest = createAccountInfo(
                username = userTextField.text.toString(),
                password = passTextField.text.toString(),
                isAdmin = false
            )
            val json = Gson().toJson(createAccountRequest)
            doPostRequest("/api/users", json, ::handleCreateAccount)
        })
        return view
    }

    fun handleCreateAccount(json: JSONObject, code: Int) {
        val handler = Handler(Looper.getMainLooper());

        handler.post({
            if (code == 200) {
                Toast.makeText(this.context, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                navigateTo(LoginFragment(), fragmentManager)
            } else {
                Toast.makeText(this.context, "Create Account Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private data class createAccountInfo(
        val username: String,
        val password: String,
        val isAdmin: Boolean
    )
}
