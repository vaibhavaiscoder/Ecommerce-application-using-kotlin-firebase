package com.example.ecomvb.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.ecomvb.activity.ProfileEditActivity
import com.example.ecomvb.R
import com.example.ecomvb.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var firbaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(layoutInflater)

        firbaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        binding.profileEditBtn.setOnClickListener {
            val intent = Intent(context, ProfileEditActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.child(firbaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val name = "${snapshot.child("name").value}"
                    val imageUrl = "${snapshot.child("imageUrl").value}"
                    val number = "${snapshot.child("number").value}"
                    val uid = "${snapshot.child("uid").value}"

                    binding.nameTv.text = name
                    binding.numberTv.text = number
                    binding.accountTypeTv.text = uid

                    try {
                        Glide.with(this@ProfileFragment).load(imageUrl)
                            .placeholder(R.drawable.ic_person_gray).into(binding.profileTv)
                    } catch (e: Exception) {

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }
}