package com.example.padelbook.models

import androidx.lifecycle.MutableLiveData

class Player {
    var name = MutableLiveData<String>()
    var location = MutableLiveData<String>()
    var matches = MutableLiveData<String>()
    var base64image : String = ""
}