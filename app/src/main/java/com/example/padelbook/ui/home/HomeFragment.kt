package com.example.padelbook.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.padelbook.R
import com.example.padelbook.databinding.FragmentHomeBinding
import com.example.padelbook.models.CreateMatch
import com.example.padelbook.service.PadelService

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val service: PadelService = PadelService();


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val email = arguments?.getString("email")

        val createButton: Button = root.findViewById(R.id.button)

        // Set an OnClickListener for the button
        createButton.setOnClickListener {
            // Call a method to handle the button click
            handleCreateButtonClick()
        }

        return root
    }

    private fun handleCreateButtonClick() {
        // Retrieve selected items from Spinners and log them
        val createMatch = CreateMatch()
        createMatch.location = view?.findViewById<Spinner>(R.id.LocationSpinner)?.selectedItem.toString()
        createMatch.time = view?.findViewById<Spinner>(R.id.TextSpinner)?.selectedItem.toString()

        createMatch.players[0] =
            view?.findViewById<Spinner>(R.id.PlayerSpinner1)?.selectedItem.toString()

        createMatch.players[1] =
            view?.findViewById<Spinner>(R.id.playerSpinner2)?.selectedItem.toString()

        createMatch.players[2] =
            view?.findViewById<Spinner>(R.id.playerSpinner3)?.selectedItem.toString()

        createMatch.players[3] =
            view?.findViewById<Spinner>(R.id.playerSpinner4)?.selectedItem.toString()

        Log.d("DropdownSelection", createMatch.location)
        Log.d("DropdownSelection", createMatch.time)
        Log.d("DropdownSelection", createMatch.players[0])
        Log.d("DropdownSelection", createMatch.players[1])
        Log.d("DropdownSelection", createMatch.players[2])
        Log.d("DropdownSelection", createMatch.players[3])

        try {
            service.createMatch(createMatch)
        } catch (e: Exception) {
            Log.d("post", "An error occurred in handleCreateButtonClick: $e")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}