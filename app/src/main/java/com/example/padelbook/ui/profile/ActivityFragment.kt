package com.example.padelbook.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.padelbook.R
import com.example.padelbook.models.SharedViewModel

class ActivityFragment : Fragment() {
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val linear_layout = view.findViewById<LinearLayout>(R.id.linear_layout)
        for (item in sharedViewModel.matchList) {
            val textView1 = TextView(requireContext())
            textView1.text = item.location.value.toString()
            textView1.height = 50;

            linear_layout.addView(textView1)
        }

    }

}