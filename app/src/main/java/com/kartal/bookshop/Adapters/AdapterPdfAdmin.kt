package com.kartal.bookshop.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kartal.bookshop.Models.ModelPdf
import com.kartal.bookshop.MyApplication
import com.kartal.bookshop.databinding.RowPdfAdminBinding

class AdapterPdfAdmin :RecyclerView.Adapter<AdapterPdfAdmin.HolderPdfAdmin>{


    //context
    private var context : Context
    //Arraylist to hold pdfs
    private var pdfArrayList:ArrayList<ModelPdf>

    //view binding
    private lateinit var binding : RowPdfAdminBinding

    //constructor
    constructor(context: Context, pdfArrayList: ArrayList<ModelPdf>) : super() {
        this.context = context
        this.pdfArrayList = pdfArrayList
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPdfAdmin {
        //bind/inflate layout row_pdfadmin.xml
        binding = RowPdfAdminBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderPdfAdmin(binding.root)
    }

    override fun onBindViewHolder(holder: HolderPdfAdmin, position: Int) {
        //Get Data - Set Data - Handle click

        //get data
        val model = pdfArrayList[position]
        val pdfId = model.id
        val categoryId = model.categoryId
        val title = model.title
        val description = model.description
        val pdfUrl = model.url
        val timestamp = model.timestamp
        //convert timestamp to dd//MM//yyyy format
        val formattedDate = MyApplication.formatTimeStamp(timestamp)

        //set data
        
    }

    override fun getItemCount(): Int {

        return pdfArrayList.size
    }

    //*View Holder class for row_pdf_admin.xml
    inner class HolderPdfAdmin(itemview: View) : RecyclerView.ViewHolder(itemview) {
        //UI views of row_pdf_admin.xml
        val pdfView = binding.pdfView
        val progressBar = binding.progressBar
        val titleTv = binding.titleTv
        val descriptionTv = binding.descriptionTv
        val categoryTv = binding.categoryTv
        val sizeTv = binding.sizeTv
        val dateTv = binding.dateTv
        val moreBtn = binding.moreBtn
    }
}