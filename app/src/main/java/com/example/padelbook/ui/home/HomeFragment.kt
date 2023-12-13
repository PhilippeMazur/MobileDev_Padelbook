package com.example.padelbook.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.padelbook.R
import com.example.padelbook.databinding.FragmentHomeBinding
import com.example.padelbook.models.CreateMatch
import com.example.padelbook.models.SharedViewModel
import com.example.padelbook.service.PadelService

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val service: PadelService = PadelService();
    val sharedViewModel: SharedViewModel by activityViewModels()



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


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
        var duplicate = false;
        val createMatch = CreateMatch()
        createMatch.location = view?.findViewById<Spinner>(R.id.LocationSpinner)?.selectedItem.toString()
        createMatch.date = view?.findViewById<EditText>(R.id.editTextDate)?.text.toString();
        createMatch.time = view?.findViewById<Spinner>(R.id.TextSpinner)?.selectedItem.toString()

        val p1 = sharedViewModel.Player.name.value
        val p2 = ""
        val p3 = ""
        val p4 = ""

        createMatch.players[0] = p1.toString()
        createMatch.players[1] = p2
        createMatch.players[2] = p3
        createMatch.players[3] = p4

        service.createMatch(createMatch)
    }

    private fun showDialog(message: String) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}