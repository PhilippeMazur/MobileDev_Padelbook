package com.example.padelbook.service

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import com.example.padelbook.R
import com.example.padelbook.models.CreateMatch
import com.example.padelbook.models.Match
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

                    checkIfPlayerIsInMatch(sharedViewModel.Player.name.value.toString(), db, sharedViewModel)

                    //sharedViewModel.location.value = document.get("location").toString()
                    //sharedViewModel.matches.value = document.get("matches").toString()
                }
            }.addOnFailureListener { exception ->
                Log.w("testing", "Error getting documents.", exception)
            }
        }
    }

    fun UpdatePreferences(email: String, sharedViewModel: SharedViewModel) {
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
                    sharedViewModel.Preferences.prefered_hand.value = document.get("prefered_hand").toString()
                    sharedViewModel.Preferences.prefered_position.value = document.get("prefered_position").toString()
                    sharedViewModel.Preferences.prefered_time.value = document.get("prefered_time").toString()
                    sharedViewModel.Preferences.prefered_match_type.value = document.get("prefered_match_type").toString()
                }
            }.addOnFailureListener { exception ->
                Log.w("testing", "Error getting documents.", exception)
            }
        }
    }

    fun checkIfPlayerIsInMatch(name:String, db:FirebaseFirestore, sharedViewModel: SharedViewModel) {

        db.collection("matches")
            .whereArrayContains("players", name)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    // No matches found
                } else {
                    // Matches found
                    for (document in querySnapshot) {
                        // Handle each document
                        val valuesList: List<Any> = document.data.values.toList()
                        val nameList: List<String> = valuesList.get(0) as List<String>
                        val match = Match()
                        match.p1.value = nameList.get(0)
                        match.p2.value = nameList.get(1)
                        match.p3.value = nameList.get(2)
                        match.p4.value = nameList.get(3)
                        match.location.value = valuesList.get(1).toString()
                        match.time.value = valuesList.get(2).toString()

                        sharedViewModel.matchList.add(match)
                        sharedViewModel.matchList.forEach { match ->
                            // Do something with match
                            Log.d("matches", match.p1.value.toString())
                            Log.d("matches", match.p2.value.toString())
                            Log.d("matches", match.p3.value.toString())
                            Log.d("matches", match.p4.value.toString())
                            Log.d("matches", match.location.value.toString())
                            Log.d("matches", match.time.value.toString())

                        }
                    }
                }
            } .addOnFailureListener {
                Log.d("matches", "error")
            }
    }

    fun createMatch(createMatch: CreateMatch) {
        val db = FirebaseFirestore.getInstance()
        // Get a reference to the 'matches' collection
        val matchesCollection = db.collection("matches")

        // Create a new document in the 'matches' collection
        matchesCollection.add(createMatch)
            .addOnSuccessListener { documentReference ->
                // Handle success
                val matchId = documentReference.id
                println("Match added successfully with ID: $matchId")
                Log.d("post", "Match added successfully with ID: $matchId")
            }
            .addOnFailureListener { e ->
                // Handle failure
                println("Error adding match to the database: $e")
                Log.d("post", "$e")

            }
    }
}

