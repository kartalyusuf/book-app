package com.kartal.bookshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PdfListAdminActivity : AppCompatActivity() {

    //category id , title
    private var categoryId = ""
    private var category = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_list_admin)

        //get from intent , that we passed from adapter
        val intent =  intent
        categoryId= intent.getStringExtra("categoryId")!!
        categoryId= intent.getStringExtra("category")!!



    }
}