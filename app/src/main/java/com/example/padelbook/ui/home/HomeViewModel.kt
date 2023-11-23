package com.example.padelbook.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    private val _textWelcome = MutableLiveData<String>().apply {
        value = "Welcome"
    }
    val textWelcome: LiveData<String> = _textWelcome

    private val _textUsername = MutableLiveData<String>().apply {
        value = "John Doe" // Replace with the actual username
    }
    val textUsername: LiveData<String> = _textUsername
}