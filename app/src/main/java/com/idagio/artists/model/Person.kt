package com.idagio.artists.model

data class Person(val id: Int, val forename: String, val surname: String) {

    val fullName: String
        get() = "$forename $surname"
}





