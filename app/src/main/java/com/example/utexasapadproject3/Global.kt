package com.example.utexasapadproject3

object Global {
    private var user: String? = null
    private var searchResult: String? = null

    fun getUser(): String? {
        return user
    }

    fun setUser(str: String) {
        user = str
    }
}