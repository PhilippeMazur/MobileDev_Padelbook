package com.example.padelbook.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.GeoPoint

class Field {
    var name = MutableLiveData<String>()
    var location = MutableLiveData<GeoPoint>()
}