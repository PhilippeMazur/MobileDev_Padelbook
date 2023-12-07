package com.example.padelbook.models

import androidx.lifecycle.MutableLiveData

class CreateMatch {
    var location: String = ""
    var date: String = ""
    var time: String = ""
    val players: MutableList<String> = MutableList(4) { "" }


}