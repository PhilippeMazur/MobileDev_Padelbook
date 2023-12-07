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
    // View lookup cache
    private class ViewHolder {
        lateinit var matchDate: TextView
        lateinit var matchLocation: TextView
        lateinit var p1: TextView
        lateinit var p2: TextView
        lateinit var p3: TextView
        lateinit var p4: TextView
        lateinit var matchTime: TextView

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val match = getItem(position)
        var viewHolder: ViewHolder
        var view: View

        if (convertView == null) {
            viewHolder = ViewHolder()
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
            viewHolder.matchLocation = view.findViewById(android.R.id.text1)
            viewHolder.matchDate = view.findViewById(android.R.id.text1)
            viewHolder.p1 = view.findViewById(android.R.id.text1)
            viewHolder.p2 = view.findViewById(android.R.id.text1)
            viewHolder.p3 = view.findViewById(android.R.id.text1)
            viewHolder.p4 = view.findViewById(android.R.id.text1)
            viewHolder.matchTime = view.findViewById(android.R.id.text1)

            view.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            view = convertView
        }

        viewHolder.matchLocation.text = match?.location?.value
        viewHolder.matchDate.text = match?.date?.value
        viewHolder.p1.text = match?.p1?.value
        viewHolder.p2.text = match?.p2?.value
        viewHolder.p3.text = match?.p3?.value
        viewHolder.p4.text = match?.p4?.value
        viewHolder.matchTime.text = match?.time?.value

        return view
    }
}