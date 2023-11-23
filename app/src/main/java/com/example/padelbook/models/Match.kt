package com.example.padelbook.models

import androidx.lifecycle.MutableLiveData

class Match {
    val p1 = MutableLiveData<String>()
    val p2 = MutableLiveData<String>()
    val p3 = MutableLiveData<String>()
    val p4 = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val time = MutableLiveData<String>()

}