package com.example.padelbook.models

import androidx.lifecycle.MutableLiveData

class Preferences {
    var prefered_hand = MutableLiveData<String>()
    var prefered_position = MutableLiveData<String>()
    var prefered_time = MutableLiveData<String>()
    var prefered_match_type = MutableLiveData<String>()
}