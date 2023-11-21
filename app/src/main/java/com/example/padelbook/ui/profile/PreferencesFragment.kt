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

class PreferencesFragment : Fragment() {

    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preferences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views and set user-specific data
        // ...


        var hand = view.findViewById<TextView>(R.id.data_preference_hand)
        var matchtype = view.findViewById<TextView>(R.id.data_preference_matchtype)
        var position = view.findViewById<TextView>(R.id.data_preference_position)
        var time = view.findViewById<TextView>(R.id.data_preference_time)
        var button = view.findViewById<TextView>(R.id.editPreferences)

        hand.text = sharedViewModel.Preferences.prefered_hand.value
        matchtype.text = sharedViewModel.Preferences.prefered_match_type.value
        position.text = sharedViewModel.Preferences.prefered_position.value
        time.text = sharedViewModel.Preferences.prefered_time.value



        button.setOnClickListener {
            val editPreferencesFragment = EditPreferencesFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, editPreferencesFragment)
            fragmentTransaction?.addToBackStack("editPreferences")
            fragmentTransaction?.commit()
        }


    }
}