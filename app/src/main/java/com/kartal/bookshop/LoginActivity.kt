package com.kartal.bookshop

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kartal.bookshop.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityLoginBinding

    //firebase auth
    private lateinit var firebaseAuth : FirebaseAuth

    //progressdialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebaseauth
        firebaseAuth = FirebaseAuth.getInstance()

        //init progressdialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click , not have a account go to register screen
        binding.noAccountTv.setOnClickListener {

        }

        //handle click login
        binding.loginBtn.setOnClickListener {

            validateData()
        }



    }
    private var email = ""
    private var password = ""

    private fun validateData() {
       //input data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this,"Invalid email format...",Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()) {
            Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show()
        }
        else {
            loginUser()
        }

    }

    private fun loginUser() {
        //login firebase auth

        //show progress
        progressDialog.setMessage("Logging in...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //success login
                checkUser()
            }
            .addOnFailureListener {e->
                //failed login
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
       //check user type
        // if User move to user dashboard
        // if Admin move to admin dashboard

        progressDialog.setMessage("Checking user...")

        val firebaseUser = firebaseAuth.currentUser!!

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    progressDialog.dismiss()

                    //get user type ( user or admin )
                    val userType = snapshot.child("userType").value
                    if (userType == "user") {
                        //its simple user , open user dashboard
                        startActivity(Intent(this@LoginActivity , DashboardUserActivity::class.java))
                        finish()

                    }
                    else if (userType == "admin") {
                        //its simple admin , open admin dashboard
                        startActivity(Intent(this@LoginActivity , DashboardAdminActivity::class.java))
                        finish()
                        

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}