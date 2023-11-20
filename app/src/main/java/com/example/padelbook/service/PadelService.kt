package com.example.padelbook.service

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import com.example.padelbook.R
import com.example.padelbook.models.SharedViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PadelService {
    fun GetData(email:String, sharedViewModel: SharedViewModel, navController: NavController) {
        // Retrieve the user's email from the intent
        if (email != null) {
            Log.d("main", email)
            sharedViewModel.email = email

        }
        // Pass the user's email to HomeFragment
        if (email != null) {
            // Pass the user's email to HomeFragment
            val bundle = Bundle()
            bundle.putString("email", email)
            navController.navigate(R.id.navigation_home, bundle)
        }
        // Get a reference to the Firestore database
        val db = FirebaseFirestore.getInstance()

        // Create a query to find the document with the matching email
        if (email != null) {
            val query: Query =
                db.collection("users").whereEqualTo("email", sharedViewModel.email.toString())
            // Retrieve the document
            query.get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {

                    //sharedViewModel.name.value = document.get("name").toString()

                    sharedViewModel.Player.name.value = document.get("name").toString()
                    sharedViewModel.Player.location.value = document.get("location").toString()
                    sharedViewModel.Player.matches.value = document.get("matches").toString()
                    sharedViewModel.Preferences.prefered_hand.value = document.get("prefered_hand").toString()
                    sharedViewModel.Preferences.prefered_position.value = document.get("prefered_position").toString()
                    sharedViewModel.Preferences.prefered_time.value = document.get("prefered_time").toString()
                    sharedViewModel.Preferences.prefered_match_type.value = document.get("prefered_match_type").toString()

                    //sharedViewModel.location.value = document.get("location").toString()
                    //sharedViewModel.matches.value = document.get("matches").toString()
                }
            }.addOnFailureListener { exception ->
                Log.w("testing", "Error getting documents.", exception)
            }
        }
    }
}