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
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.padelbook.R
import com.example.padelbook.models.SharedViewModel
import com.google.android.material.tabs.TabLayout
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
        matchesPlayedTextView.text = sharedViewModel.matchList.size.toString()

        val tabLayout = view.findViewById<TabLayout>(R.id.navigationTabs)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragmentManager = childFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                when (tab.position) {
                    0 -> {
                        val activityFragment = ActivityFragment()
                        fragmentTransaction.replace(R.id.showFragment, activityFragment)
                    }
                    1 -> {
                        val preferencesFragment = PreferencesFragment()
                        fragmentTransaction.replace(R.id.showFragment, preferencesFragment)
                    }
                }

                fragmentTransaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }



}
