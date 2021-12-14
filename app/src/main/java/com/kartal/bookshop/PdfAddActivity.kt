package com.kartal.bookshop

import android.app.ProgressDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kartal.bookshop.Models.ModelCategory
import com.kartal.bookshop.databinding.ActivityPdfAddBinding

class PdfAddActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityPdfAddBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress
    private lateinit var progressDialog: ProgressDialog

    //arraylist to hold pdf categories
    private lateinit var categoryArrayList: ArrayList<ModelCategory>

    //uri of picked pdf
    private var pdfUri: Uri? = null

    //TAG
    private val TAG = "PDF_ADD_TAG"



    //book id get from intent started from AdapterPdfAdmin
    private var bookId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        loadPdfCategories()

        //setup progress
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)




    }

    private fun loadPdfCategories() {
        Log.d(TAG,"loadPdfCategories: Loading pdf categories")
        //init arraylist
        categoryArrayList = ArrayList()

        //db reference to load categories DF > categories
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before adding data
                categoryArrayList.clear()
                for (ds in snapshot.children) {
                    //get data
                    val model = ds.getValue(ModelCategory::class.java)
                    //add to arraylist
                    categoryArrayList.add(model!!)
                    Log.d(TAG,"onDataChange: ${model.category}")

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun categoryPickDialog() {
        Log.d(TAG,"categoryPickDialog: Showing pdf category pick dialog")

        //get string array of categories from arraylist
        val categoriesArray = arrayOfNulls<String>(categoryArrayList.size)
        for (i in categoryArrayList.indices) {
            categoriesArray[i] = categoryArrayList[i].category
            
        }
    }
}