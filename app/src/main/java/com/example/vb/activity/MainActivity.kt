package com.example.ecomvb.activity

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.ecomvb.R
import com.example.ecomvb.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // For searching in firebase database

        binding.searchBar.setOnClickListener {
            val intent = Intent(this,SearchActivity2::class.java)
            startActivity(intent)
        }


        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        binding.bottomBar.onItemSelected = {
            when (it) {
                0 -> {
                    i = 0;
                    navController.navigate(R.id.homeFragment)
                }
                1 -> navController.navigate(R.id.cartFragment)
                2 -> navController.navigate(R.id.moreFragment)
                3 -> navController.navigate(R.id.profileFragment)
            }
        }
//
//        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
//            override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
//                title = when(destination.id){
//                    R.id.cartFragment -> "My Cart"
//                    R.id.moreFragment -> "My Dashboard"
//                    R.id.profileFragment -> "My Profile"
//                    else -> "K- STAR"
//                }
//            }
//
//        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (i == 0) {
            finish()
        }
    }


}