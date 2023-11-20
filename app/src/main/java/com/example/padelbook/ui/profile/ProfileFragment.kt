package com.example.padelbook.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.padelbook.R
import com.example.padelbook.models.SharedViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ProfileFragment : Fragment() {

    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views and set user-specific data
        val userNameTextView = view.findViewById<TextView>(R.id.textViewUserName)
        val locationTextView = view.findViewById<TextView>(R.id.textViewLocation)
        val matchesPlayedTextView = view.findViewById<TextView>(R.id.textViewMatchesPlayed)

        userNameTextView.text = sharedViewModel.Player.name.value
        locationTextView.text = sharedViewModel.Player.location.value
        matchesPlayedTextView.text = sharedViewModel.Player.matches.value
        



    }
}
