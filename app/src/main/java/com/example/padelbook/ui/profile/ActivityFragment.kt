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

class ActivityFragment : Fragment() {
    val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var matchLV: ListView
    lateinit var matchList: MutableList<Match>
    private var _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val numbers = listOf("one", "two", "three", "four")
        val adapter = ActivityAdapter(numbers)
        val recyclerView = binding.matchRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return root
    }
}