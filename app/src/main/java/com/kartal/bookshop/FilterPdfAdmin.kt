package com.kartal.bookshop

import android.widget.Filter
import com.kartal.bookshop.Adapters.AdapterPdfAdmin
import com.kartal.bookshop.Models.ModelPdf

//used to filter data from recyclerview  | search pdf from pdf list in recyclerview

class FilterPdfAdmin:Filter{

    //arraylist in which we want to search
    var filterList : ArrayList<ModelPdf>
    //adapter in which filter need to be implemented
    var adapterPdfAdmin:AdapterPdfAdmin

    constructor(filterList: ArrayList<ModelPdf>, adapterPdfAdmin: AdapterPdfAdmin) {
        this.filterList = filterList
        this.adapterPdfAdmin = adapterPdfAdmin
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint:CharSequence? = constraint //value to search
        val result = FilterResults()
        //value to be searched should not be null and not empty
        if (constraint != null && constraint.isNotEmpty()) {
            //change to upper case or lowercase to avoid case sensitivity
            constraint=constraint.toString().lowercase()
            var filteredModels = ArrayList<ModelPdf>()
            for (i in filterList.indices) {
                //validate if match
                if (filterList[i].title.lowercase().contains(constraint)) {
                    //searched value is similar to value in list , add to filtered list
                    filteredModels.add(filterList[i])

                }
            }
            result.count = filteredModels.size
            result.values = filteredModels

        }
        else {
            //searched value is either null or empty , return all data
            result.count = filterList.size
            result.values = filterList

        }
        return result //don't miss


    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        //apply filter changes
        adapterPdfAdmin.pdfArrayList = results.values as ArrayList<ModelPdf>

        //notify changes
        adapterPdfAdmin.notifyDataSetChanged()
    }


}