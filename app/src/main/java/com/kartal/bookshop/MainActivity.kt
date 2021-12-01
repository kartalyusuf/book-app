package com.kartal.bookshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kartal.bookshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //handle click login
        binding.loginBtn.setOnClickListener {
            //will do later
        }

        //handle click, skip and continue to main screen
        binding.skipBtn.setOnClickListener {
            //will do later

        }


    }
}