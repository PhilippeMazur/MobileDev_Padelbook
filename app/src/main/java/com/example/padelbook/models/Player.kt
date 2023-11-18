package com.example.padelbook.models

import androidx.lifecycle.MutableLiveData

class Player {
    val name = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val matches = MutableLiveData<String>()
}