package com.example.padelbook.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

        // Retrieve the email from the arguments
        val email = sharedViewModel.email

        // Get a reference to the Firestore database
        val db = FirebaseFirestore.getInstance()

        // Create a query to find the document with the matching email
        if (email != null) {
            Log.d("testing", email)
            val query: Query = db.collection("users").whereEqualTo("email", email.toString())
            // Retrieve the document
            query.get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    Log.d("testing", "${document.id} => ${document.data}")

                    sharedViewModel.name = document.getString("name")
                    sharedViewModel.location = document.getString("location")
                    sharedViewModel.matches = document.getString("matches")
                //do try catch


                    // Use the document data to set the user-specific data
                    //userNameTextView.text = document.getString("name")
                    //locationTextView.text = document.getString("location")
                    //matchesPlayedTextView.text = document.getString("matches")
                }
            }.addOnFailureListener { exception ->
                Log.w("testing", "Error getting documents.", exception)
            }
        }


    }
}
