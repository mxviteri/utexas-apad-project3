package com.example.utexasapadproject3

class Event {
    var id: String? = null
    var name: String? = null
    var venue: String? = null
    var datetime: String? = null
    var capacity: String? = null
    var description: String? = null
    var items: MutableList<String>? = null

    constructor(id: String, name: String, venue: String, datetime: String, capacity: String, description: String, items: MutableList<String>) {
        this.id = id
        this.name = name.capitalize()
        this.venue = venue.capitalize()
        this.datetime = datetime
        this.capacity = capacity
        this.description = description.capitalize()
        this.items = items
    }
}