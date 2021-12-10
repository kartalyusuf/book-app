package com.kartal.bookshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kartal.bookshop.databinding.ActivityPdfAddBinding

class PdfAddActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityPdfAddBinding

    //book id get from intent started from AdapterPdfAdmin
    private var bookId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}