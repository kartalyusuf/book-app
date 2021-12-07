package com.kartal.bookshop

import com.kartal.bookshop.Adapters.AdapterCategory
import com.kartal.bookshop.Models.ModelCategory
import java.util.logging.Filter

class FilterCategory : android.widget.Filter{

    //array list in which we want to search
    private var filterList :ArrayList<ModelCategory>

    //adapter in which filter need to be implement
    private var adapterCategory : AdapterCategory

    //constructor
    constructor(filterList: ArrayList<ModelCategory>, adapterCategory: AdapterCategory) {
        this.filterList = filterList
        this.adapterCategory = adapterCategory
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        //value should not be null and not empty
        if (constraint != null && constraint.isNotEmpty()) {
            //searched value is nor null not empty


            //change to uppercase or lowercase to avoid case sensitivity
            constraint = constraint.toString().uppercase()
            val filterModels : ArrayList<ModelCategory> = ArrayList()

            for (i in 0 until filterList.size) {
                //validate
                if (filterList[i].category.uppercase().contains(constraint)) {
                    //add to filtered list
                    filterModels.add(filterList[i])

                }
            }
            results.count = filterModels.size
            results.values = filterModels

        }
        else {
            //search value is either null or empty
            results.count = filterList.size
            results.values = filterList

        }
        return results //dont miss it


    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        //apply filter changes
        adapterCategory.categoryArrayList = results.values as ArrayList<ModelCategory>

        //notify changes
        adapterCategory.notifyDataSetChanged()
    }
}