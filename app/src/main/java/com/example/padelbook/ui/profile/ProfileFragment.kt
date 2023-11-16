package com.example.padelbook.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.padelbook.R

class ProfileFragment : Fragment() {

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

        // Replace the following with actual user data
        userNameTextView.text = "John Doe"
        locationTextView.text = "Plays in: New York"
        matchesPlayedTextView.text = "50"
    }
}
