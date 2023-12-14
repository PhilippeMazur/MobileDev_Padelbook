package com.example.padelbook.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.padelbook.R
import com.example.padelbook.databinding.FragmentActivityBinding
import com.example.padelbook.models.Match
import com.example.padelbook.models.SharedViewModel
import com.example.padelbook.service.PadelService
import com.google.firebase.firestore.FirebaseFirestore

class ActivityFragment : Fragment() {
    val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var matchList: MutableList<Match>
    private var _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = FirebaseFirestore.getInstance();
        val service: PadelService = PadelService();
        service.checkIfPlayerIsInMatch(sharedViewModel.Player.name.value.toString() ,db, sharedViewModel);

        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        val root: View = binding.root
        matchList = sortMatchesByDateDescending( sharedViewModel.matchList )
        val adapter = ActivityAdapter(matchList)
        val recyclerView = binding.matchRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return root
    }

    private fun sortMatchesByDateDescending(matches: List<Match>): MutableList<Match> {
        return matches.sortedByDescending { it.getDateAsDate() }
            .toMutableList()
    }
}