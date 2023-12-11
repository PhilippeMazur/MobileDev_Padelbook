package com.example.padelbook.service

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import com.example.padelbook.R
import com.example.padelbook.models.CreateMatch
import com.example.padelbook.models.Match
import com.example.padelbook.models.Player
import com.example.padelbook.models.SharedViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await


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
                    sharedViewModel.Player.name.value = document.get("name").toString()
                    sharedViewModel.Player.location.value = document.get("location").toString()
                    sharedViewModel.Player.matches.value = document.get("matches").toString()
                    sharedViewModel.Player.base64image = document.get("image").toString()
                    sharedViewModel.Preferences.prefered_hand.value = document.get("prefered_hand").toString()
                    sharedViewModel.Preferences.prefered_position.value = document.get("prefered_position").toString()
                    sharedViewModel.Preferences.prefered_time.value = document.get("prefered_time").toString()
                    sharedViewModel.Preferences.prefered_match_type.value = document.get("prefered_match_type").toString()

                    checkIfPlayerIsInMatch(sharedViewModel.Player.name.value.toString(), db, sharedViewModel)
                    getAllMatches(db, sharedViewModel)

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

                        val players = document["players"].toString()
                        val playersArray = players.split(", ")
                        val match = Match()
                        match.date.value = document.get("date").toString()
                        var namePlayer1 = playersArray[0].substring(1)
                        val namePlayer4 = playersArray[3]
                        var namePlayer42 = namePlayer4.substring(0, namePlayer4.length - 1)
                        match.p1.value = namePlayer1
                        match.p2.value = playersArray[1]
                        match.p3.value = playersArray[2]
                        match.p4.value = namePlayer42
                        match.location.value = document.get("location").toString()
                        match.time.value = document.get("time").toString()

                        sharedViewModel.matchList.add(match)
                        sharedViewModel.matchList.forEach { match ->
                            // Do something with match
                            /*
                            Log.d("matches", "player 1:"+match.p1.value.toString())
                            Log.d("matches", match.p2.value.toString())
                            Log.d("matches", match.p3.value.toString())
                            Log.d("matches", "player 4:"+match.p4.value.toString())
                            Log.d("matches", match.location.value.toString())
                            Log.d("matches", match.time.value.toString())
                            */
                        }
                    }
                }
            } .addOnFailureListener {
                Log.d("matches", "error")
            }
    }

    fun getAllMatches(db:FirebaseFirestore, sharedViewModel: SharedViewModel) {

        db.collection("matches")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    // No matches found
                } else {
                    // Matches found
                    for (document in querySnapshot) {
                        // Handle each document
                        val valuesList: List<Any> = document.data.values.toList()

                        val players = document["players"].toString()
                        val playersArray = players.split(", ")
                        val match = Match()
                        match.date.value = document.get("date").toString()
                        var namePlayer1 = playersArray[0].substring(1)
                        val namePlayer4 = playersArray[3]
                        var namePlayer42 = namePlayer4.substring(0, namePlayer4.length - 1)
                        match.p1.value = namePlayer1
                        match.p2.value = playersArray[1]
                        match.p3.value = playersArray[2]
                        match.p4.value = namePlayer42
                        match.location.value = document.get("location").toString()
                        match.time.value = document.get("time").toString()

                        sharedViewModel.allMatches.add(match)
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
    fun getPlayerByName(name: String, onPlayerLoaded: ((Player) -> Unit)?) {
        val db = FirebaseFirestore.getInstance()
        val player : Player = Player()
        val query: Query = db.collection("users").whereEqualTo("name", name)

        query.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                player.name.value = document.get("name").toString()
                player.location.value = document.get("location").toString()
                player.base64image = document.get("image").toString()
                player.matches.value = document.get("matches").toString()
            }

            // Call the callback function when the Firestore query completes
            onPlayerLoaded?.invoke(player)
        }.addOnFailureListener { exception ->
            Log.w("testing", "Error getting documents.", exception)
        }
    }
}

