package com.kartal.bookshop

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.kartal.bookshop.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding:ActivityRegisterBinding

    //firebase auth
    private lateinit var firebaseAuth : FirebaseAuth

    //progressdialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebaseauth
        firebaseAuth = FirebaseAuth.getInstance()

        //init progressdialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click, begin register
        binding.registerBtn.setOnClickListener {

            validateData()
        }
    }

    private var name = ""
    private var email = ""
    private var password = ""

    private fun validateData() {
        //1)input data
        name = binding.nameEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password= binding.passwordEt.text.toString().trim()
        val cPassword = binding.cPasswordEt.text.toString().trim()

        //2)validate data
        if (name.isEmpty()){

            Toast.makeText(this,"Enter your name...",Toast.LENGTH_SHORT).show()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this,"Invalid Email Pattern",Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()) {
            Toast.makeText(this,"Enter your password",Toast.LENGTH_SHORT).show()
        }
        else if (cPassword.isEmpty()) {
            Toast.makeText(this,"Confirm password",Toast.LENGTH_SHORT).show()
        }
        else if (password!=cPassword) {
            Toast.makeText(this,"Password doesn't match",Toast.LENGTH_SHORT).show()
        }
        else{
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        //show progress
        progressDialog.setMessage("Creating Account")
        progressDialog.show()

        //create user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //account created , now add user info in database
                uptadeUserInfo()
            }
            .addOnFailureListener{ e->
                //failed creating account
                Toast.makeText(this,"Failed creating account due to ${e.message}",Toast.LENGTH_SHORT).show()
            }

    }

    private fun uptadeUserInfo() {
        //Save user info  - Firebase RealTime Database
        progressDialog.setMessage("Saving User Info...")

        //timestamp
        val timestamp = System.currentTimeMillis()

        //Get current user id , since user is registered so we can get it now
        val uid = firebaseAuth.uid

        //setup data to add in database
        val hashMap : HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["profileImage"] = ""
        hashMap["userType"] = "user"
        hashMap["timestamp"] = timestamp


        //set data to database
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                //user info saved , open your dashboard
                Toast.makeText(this,"Account Created",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity,DashboardUserActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                //failed adding to db
                Toast.makeText(this,"Failed saving user info due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }
}