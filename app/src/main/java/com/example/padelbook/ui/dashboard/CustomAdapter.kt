package com.example.padelbook.ui.dashboard

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.padelbook.R
import com.example.padelbook.models.Match
import com.example.padelbook.models.Player
import com.example.padelbook.models.SharedViewModel
import com.example.padelbook.service.PadelService

class CustomAdapter(private val items: List<Match>, private val name: MutableLiveData<String>, base64image: String) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    val service: PadelService = PadelService();
    var player1: Player = Player();
    var player2: Player = Player();
    var player3: Player = Player();
    var player4: Player = Player();
    var base64Image = base64image
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.date)
        var time: TextView = itemView.findViewById(R.id.time)
        var location: TextView = itemView.findViewById(R.id.location)
        var profilePictureP1: ImageView = itemView.findViewById(R.id.profilePictureP1)
        var p1: TextView = itemView.findViewById(R.id.p1)

        var profilePictureP2: ImageView = itemView.findViewById(R.id.profilePictureP2)
        var p2: TextView = itemView.findViewById(R.id.p2)

        var profilePictureP3: ImageView = itemView.findViewById(R.id.profilePictureP3)
        var p3: TextView = itemView.findViewById(R.id.p3)

        var profilePictureP4: ImageView = itemView.findViewById(R.id.profilePictureP4)
        var p4: TextView = itemView.findViewById(R.id.p4)

        val joinButton: Button = itemView.findViewById(R.id.joinButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = items[position]
        holder.joinButton.visibility = View.VISIBLE

        Log.d("players", "location: " + match.p1.value.toString())
        //assign player data
        if(match != null) {
            service.getPlayerByName(match.p1.value.toString()) { player ->
                player1 = player
                if (player1 != null) {
                    holder.date.text = match.date.value
                    holder.time.text = match.time.value
                    holder.location.text = match.location.value

                    val decodedString1 = Base64.decode(base64Image, Base64.DEFAULT)
                    val decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
                    holder.profilePictureP1.setImageBitmap(decodedByte1)
                    holder.p1.text = player1.name.value
                }
            }
            service.getPlayerByName(match.p2.value.toString()) { player ->
                player2 = player
                if (player2 != null) {
                    val decodedString1 = Base64.decode(base64Image, Base64.DEFAULT)
                    val decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
                    holder.profilePictureP2.setImageBitmap(decodedByte1)
                    holder.p2.text = player2.name.value
                }
            }
            service.getPlayerByName(match.p3.value.toString()) { player ->
                player3 = player
                if (player3 != null) {
                    val decodedString1 = Base64.decode(base64Image, Base64.DEFAULT)
                    val decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
                    holder.profilePictureP3.setImageBitmap(decodedByte1)
                    holder.p3.text = player3.name.value
                }
            }
            service.getPlayerByName(match.p4.value.toString()) { player ->
                player4 = player
                if (player4 != null) {
                    val decodedString1 = Base64.decode(base64Image, Base64.DEFAULT)
                    val decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
                    holder.profilePictureP4.setImageBitmap(decodedByte1)
                    holder.p4.text = player4.name.value
                }
            }

            holder.joinButton.setOnClickListener {
                if(match.p1.value.toString() != "" && match.p2.value.toString() != "" && match.p3.value.toString() != "" && match.p4.value.toString() != "") {
                    showDialog("No available spaces left", holder, match)

                } else {
                    if(!checkIfPlayerIsInMatch(name, match)) {
                        showDialog("Successful", holder, match)
                        if(match.p2.value.toString() == "") {
                            val decodedString1 = Base64.decode(player1.base64image, Base64.DEFAULT)
                            val decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
                            holder.profilePictureP2.setImageBitmap(decodedByte1);
                            holder.p2.text = name.value.toString();
                        } else if(match.p3.value.toString() == "") {
                            val decodedString1 = Base64.decode(player1.base64image, Base64.DEFAULT)
                            val decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
                            holder.profilePictureP3.setImageBitmap(decodedByte1);
                            holder.p3.text = name.value.toString();
                        } else if(match.p4.value.toString() == "") {
                            val decodedString1 = Base64.decode(player1.base64image, Base64.DEFAULT)
                            val decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
                            holder.profilePictureP4.setImageBitmap(decodedByte1);
                            holder.p4.text = name.value.toString();
                        }

                        service.updatePlayerInMatch(match.matchId, name)
                    } else {
                        showDialog("You are already registered!", holder, match)
                    }

                }
            }
        }

    }

    private fun checkIfPlayerIsInMatch(name: MutableLiveData<String>, match: Match): Boolean {
        if(match.p1.value.equals(name.value.toString())) {
            Log.d("nametesting", "he is inside homie")
            return true
        }else if(match.p2.value.equals(name.value.toString())) {
            return true
        } else if(match.p3.value.equals(name.value.toString())) {
            return true
        } else if(match.p4.value.equals(name.value.toString())) {
            return true
        } else {
            Log.d("nametesting", "not found homie")
            return false
        }
    }
    private fun showDialog(message: String, holder: ViewHolder, match: Match) {
        val builder = AlertDialog.Builder(holder.itemView.context)
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        builder.show()
    }

    override fun getItemCount(): Int {
        return items.size
    }



}