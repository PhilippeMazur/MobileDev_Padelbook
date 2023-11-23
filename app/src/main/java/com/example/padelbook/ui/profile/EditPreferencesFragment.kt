package com.example.padelbook.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.padelbook.R
import com.example.padelbook.models.SharedViewModel
import com.example.padelbook.service.PadelService
import com.google.firebase.firestore.FirebaseFirestore

class EditPreferencesFragment : Fragment() {
    val sharedViewModel: SharedViewModel by activityViewModels()
    val service: PadelService = PadelService();


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editpreferences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add any additional setup code here
        var button = view.findViewById<TextView>(R.id.savePreferences)
        val edit_preference_hand = view.findViewById<TextView>(R.id.edit_preference_hand)
        val edit_preference_position= view.findViewById<TextView>(R.id.edit_preference_position)
        val edit_preference_time= view.findViewById<TextView>(R.id.edit_preference_time)
        val edit_preference_matchtype= view.findViewById<TextView>(R.id.edit_preference_matchtype)

        button.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("users")
            val userDoc = docRef.whereEqualTo("email", sharedViewModel.email)

            userDoc.get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val docRef = db.collection("users").document(document.id)
                    val prefs = mapOf(
                        "prefered_hand" to edit_preference_hand.text.toString(),
                        "prefered_position" to edit_preference_position.text.toString(),
                        "prefered_time" to edit_preference_time.text.toString(),
                        "prefered_match_type" to edit_preference_matchtype.text.toString()
                    )
                    docRef.update(prefs).addOnSuccessListener { Log.d("updateDB", "Document successfully updated!") }
                        .addOnFailureListener { e -> Log.w("updateDB", "Error updating document", e) }
                }
            }.addOnFailureListener { e -> Log.w("updateDB", "Error getting documents", e) }
            sharedViewModel.Preferences.prefered_hand.value = edit_preference_hand.text.toString();
            sharedViewModel.Preferences.prefered_position.value = edit_preference_position.text.toString();
            sharedViewModel.Preferences.prefered_time.value = edit_preference_time.text.toString();
            sharedViewModel.Preferences.prefered_match_type.value = edit_preference_matchtype.text.toString();
            findNavController().navigateUp()
        }

    }
}