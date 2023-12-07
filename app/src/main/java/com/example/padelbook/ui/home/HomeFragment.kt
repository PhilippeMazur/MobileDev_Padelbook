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

        val p1 = view?.findViewById<Spinner>(R.id.PlayerSpinner1)?.selectedItem.toString()
        val p2 = view?.findViewById<Spinner>(R.id.playerSpinner2)?.selectedItem.toString()
        val p3 = view?.findViewById<Spinner>(R.id.playerSpinner3)?.selectedItem.toString()
        val p4 = view?.findViewById<Spinner>(R.id.playerSpinner4)?.selectedItem.toString()

        createMatch.players[0] = p1

        if(createMatch.players[0] != p2) {
            createMatch.players[1] = p2
        } else {
            duplicate = true;
        }

        if((createMatch.players[0] != p3 && createMatch.players[1] != p3)) {
            createMatch.players[2] = p3
        }else {
            duplicate = true;
        }

        if((createMatch.players[0] != p4 && createMatch.players[1] != p4 && createMatch.players[2] != p4)) {
            createMatch.players[3] = p4
        }else {
            duplicate = true;

        }


        if(!duplicate) {
            Log.d("DropdownSelection", createMatch.location)
            Log.d("DropdownSelection", createMatch.time)
            Log.d("DropdownSelection", createMatch.players[0])
            Log.d("DropdownSelection", createMatch.players[1])
            Log.d("DropdownSelection", createMatch.players[2])
            Log.d("DropdownSelection", createMatch.players[3])

            try {
                service.createMatch(createMatch);
                showDialog("Match has been created!")

            } catch (e: Exception) {
                Log.d("post", "An error occurred in handleCreateButtonClick: $e")
            }
        } else {
            showDialog("Players can't be chosen more than once!")
        }

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