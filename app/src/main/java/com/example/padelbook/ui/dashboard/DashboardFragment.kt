package com.example.padelbook.ui.dashboard

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padelbook.databinding.FragmentDashboardBinding
import com.example.padelbook.models.SharedViewModel
import com.example.padelbook.service.PadelService
import com.google.firebase.firestore.FirebaseFirestore

class DashboardFragment : Fragment() {
    val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = CustomAdapter(sharedViewModel.allMatches, sharedViewModel.Player.name, sharedViewModel.Player.base64image)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val db = FirebaseFirestore.getInstance();
        val service: PadelService = PadelService();
        service.getAllMatches(db, sharedViewModel)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}