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
import androidx.recyclerview.widget.RecyclerView
import com.example.padelbook.R
import com.example.padelbook.models.Match
import com.example.padelbook.models.SharedViewModel

class ActivityFragment : Fragment() {
    val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var matchLV: ListView
    lateinit var matchList: MutableList<Match>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        matchLV = view.findViewById(R.id.match_list_view)
        matchList = sharedViewModel.matchList

        val adapter = MatchAdapter(requireActivity(), matchList)
        matchLV.adapter = adapter
    }

}

class MatchAdapter(context: Context, matches: List<Match>) : ArrayAdapter<Match>(context, 0, matches) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val match = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)

        // Assuming you have TextViews in your simple_list_item_1 layout with ids text1
        val matchName = view.findViewById<TextView>(android.R.id.text1)

        matchName.text = match?.location?.value

        return view
    }
}