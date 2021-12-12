package com.kartal.bookshop.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.kartal.bookshop.FilterPdfAdmin
import com.kartal.bookshop.Models.ModelPdf
import com.kartal.bookshop.MyApplication
import com.kartal.bookshop.databinding.RowPdfAdminBinding

class AdapterPdfAdmin :RecyclerView.Adapter<AdapterPdfAdmin.HolderPdfAdmin>,Filterable{


    //context
    private var context : Context
    //Arraylist to hold pdfs
    public var pdfArrayList:ArrayList<ModelPdf>
    private val filterList:ArrayList<ModelPdf>


    //view binding
    private lateinit var binding : RowPdfAdminBinding

    //filter object
    var filter : FilterPdfAdmin? = null

    //constructor
    constructor(context: Context, pdfArrayList: ArrayList<ModelPdf>) : super() {
        this.context = context
        this.pdfArrayList = pdfArrayList
        this.filterList = pdfArrayList
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
        holder.titleTv.text = title
        holder.descriptionTv.text = description
        holder.dateTv.text = formattedDate

        //load further details like category, pdf from url , pdf size

        //load category
        MyApplication.loadCategory(categoryId,holder.categoryTv)

        //we don't need page number here, pas null for page number || load pdf thumbnail
        MyApplication.loadPfdFromUrlSinglePage(pdfUrl,title,holder.pdfView,holder.progressBar,null)

        //load pdf size
        MyApplication.loadPdfSize(pdfUrl,title,holder.sizeTv)

    }

    override fun getItemCount(): Int {

        return pdfArrayList.size
    }

    override fun getFilter(): Filter {
        if (filter==null) {
            filter= FilterPdfAdmin(filterList,this)
        }
        return filter as FilterPdfAdmin
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