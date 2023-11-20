package com.example.padelbook

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.padelbook.databinding.ActivityMainBinding
import com.example.padelbook.models.SharedViewModel
import com.example.padelbook.service.PadelService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val sharedViewModel: SharedViewModel by viewModels()
    val service: PadelService = PadelService();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        var email = ""
        try {
            email = intent.getStringExtra("email").toString()
            service.GetData(email, sharedViewModel, navController)

        } catch (e:Exception) {
            Log.d("error", "email is not found")
        }
        /*
        // Retrieve the user's email from the intent
        val email = intent.getStringExtra("email")
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
            val query: Query = db.collection("users").whereEqualTo("email", sharedViewModel.email.toString())
            // Retrieve the document
            query.get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {

                    //sharedViewModel.name.value = document.get("name").toString()

                    sharedViewModel.Player.name.value = document.get("name").toString()
                    sharedViewModel.Player.location.value = document.get("location").toString()
                    sharedViewModel.Player.matches.value = document.get("matches").toString()

                    //sharedViewModel.location.value = document.get("location").toString()
                    //sharedViewModel.matches.value = document.get("matches").toString()
                }
            }.addOnFailureListener { exception ->
                Log.w("testing", "Error getting documents.", exception)
            }
        }
         */
    }
}