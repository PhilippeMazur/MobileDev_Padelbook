package com.example.padelbook.models

import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Match {
    val date = MutableLiveData<String>()
    val p1 = MutableLiveData<String>()
    val p2 = MutableLiveData<String>()
    val p3 = MutableLiveData<String>()
    val p4 = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val time = MutableLiveData<String>()

    fun getDateAsDate(): Date? {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        return try {
            dateFormat.parse(date.value)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}