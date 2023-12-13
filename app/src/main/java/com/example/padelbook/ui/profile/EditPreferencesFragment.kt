package com.example.padelbook.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.padelbook.R
import com.example.padelbook.models.SharedViewModel
import com.example.padelbook.service.PadelService
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class EditPreferencesFragment : Fragment() {
    val sharedViewModel: SharedViewModel by activityViewModels()
    val service: PadelService = PadelService();
    val storageRef = Firebase.storage.reference;



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editpreferences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add any additional setup code here
        val profilePictureButton = view.findViewById<Button>(R.id.changeProfilePictureButton);
        var button = view.findViewById<TextView>(R.id.savePreferences)
        //createMatch.location = view?.findViewById<Spinner>(R.id.LocationSpinner)?.selectedItem.toString()
        val edit_preference_hand = view.findViewById<Spinner>(R.id.edit_preference_hand)
        val edit_preference_position= view.findViewById<Spinner>(R.id.edit_preference_position)
        val edit_preference_time= view.findViewById<Spinner>(R.id.edit_preference_time)
        val edit_preference_matchtype= view.findViewById<Spinner>(R.id.edit_preference_matchtype)

        var base64Image = "";

        val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri: Uri? = data?.data
                uri?.let {
                    val inputStream = requireActivity().contentResolver.openInputStream(it)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    base64Image = bitmapToBase64(bitmap)
                    // Save base64Image in a variable
                }
            }
        }

        profilePictureButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }

        button.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("users")
            val userDoc = docRef.whereEqualTo("email", sharedViewModel.email)

            userDoc.get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val docRef = db.collection("users").document(document.id)
                    if(base64Image != "") {
                        val prefs = mapOf(
                            "prefered_hand" to edit_preference_hand.selectedItem.toString(),
                            "prefered_position" to edit_preference_position.selectedItem.toString(),
                            "prefered_time" to edit_preference_time.selectedItem.toString(),
                            "prefered_match_type" to edit_preference_matchtype.selectedItem.toString(),
                            "image" to base64Image
                        )
                        docRef.update(prefs).addOnSuccessListener { Log.d("updateDB", "Document successfully updated!") }
                            .addOnFailureListener { e -> Log.w("updateDB", "Error updating document", e) }
                    } else {
                        val prefs = mapOf(
                            "prefered_hand" to edit_preference_hand.selectedItem.toString(),
                            "prefered_position" to edit_preference_position.selectedItem.toString(),
                            "prefered_time" to edit_preference_time.selectedItem.toString(),
                            "prefered_match_type" to edit_preference_matchtype.selectedItem.toString()
                        )
                        docRef.update(prefs).addOnSuccessListener { Log.d("updateDB", "Document successfully updated!") }
                            .addOnFailureListener { e -> Log.w("updateDB", "Error updating document", e) }
                    }

                }
            }.addOnFailureListener { e -> Log.w("updateDB", "Error getting documents", e) }
            sharedViewModel.Preferences.prefered_hand.value = edit_preference_hand.selectedItem.toString();
            sharedViewModel.Preferences.prefered_position.value = edit_preference_position.selectedItem.toString();
            sharedViewModel.Preferences.prefered_time.value = edit_preference_time.selectedItem.toString();
            sharedViewModel.Preferences.prefered_match_type.value = edit_preference_matchtype.selectedItem.toString();
            findNavController().navigateUp()
        }
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1
    }

}