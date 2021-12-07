package com.kartal.bookshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.kartal.bookshop.databinding.ActivityDashboardAdminBinding

class DashboardAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardAdminBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //arraylist to hold categories
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebaseauth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
            loadCategories()

        }

        //handle click , start and category page
        binding.addCategoryBtn.setOnClickListener {
            startActivity(Intent(this, CategoryAddActivity :: class.java))

        }

    }

    private fun loadCategories() {

    }

    private fun checkUser() {
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            //not logged in , go to main screen
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else {
            //logged in , get and show user info
            val email = firebaseUser.email
            //set to textview of toolbar
            binding.subTitleTv.text = email

        }
    }
}