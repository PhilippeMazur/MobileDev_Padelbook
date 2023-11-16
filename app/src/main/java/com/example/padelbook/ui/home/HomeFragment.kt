package com.example.padelbook.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.padelbook.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

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

        val textUsername: TextView = binding.textUsername
        val buttonBookCourt: Button = binding.buttonBookCourt

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = "Welcome " + email + "!"
        }

        homeViewModel.textUsername.observe(viewLifecycleOwner) {
            textUsername.text = it
        }

        // Set OnClickListener for the "Book a Court" button
        buttonBookCourt.setOnClickListener {
            // Add your code for handling the "Book a Court" button click event
            // e.g., navigate to the booking screen
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}