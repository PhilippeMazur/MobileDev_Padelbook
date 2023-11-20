package com.example.padelbook.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var email: String? = null
    val name = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val matches = MutableLiveData<String>()
    val Player: Player = Player();
    val Preferences: Preferences = Preferences();


}