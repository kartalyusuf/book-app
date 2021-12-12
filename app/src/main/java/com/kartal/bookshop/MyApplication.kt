package com.kartal.bookshop

import android.app.Application
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


import java.util.*


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object{

        fun formatTimeStamp(timestamp : Long) : String {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timestamp

            return DateFormat.format("dd/mm/yyyy",cal).toString()

        }

        //function to get pdf size
        fun loadPdfSize(pdfUrl : String, pdfTitle :String ,sizeTv :TextView) {
            val TAG = "PDF_TAG_SİZE"

            //using url we can get file and its madata from firebase storage
            val ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
            ref.metadata
                .addOnSuccessListener {storageMetadata->
                    Log.d(TAG,"loadPdfSize: got metadata")
                    val bytes = storageMetadata.sizeBytes.toDouble()
                    Log.d(TAG,"loadPdfSize : Size Bytes $bytes")

                    //convert bytes to KB/MB
                    val kb = bytes/1024
                    val mb = kb/1024
                    if (mb>=1) {
                        sizeTv.text = "${String.format("$.2f, mb")} MB"

                    }else if (kb>=1){
                        sizeTv.text = "${String.format("$.2f, kb")} KB"

                    }else {
                        sizeTv.text = "${String.format("$.2f, kb")} KB"
                    }

                }
                .addOnFailureListener {e->
                    Log.d(TAG,"loadPdfSize: Failed to get metadata due to ${e.message}")

                }
        }

        fun loadPfdFromUrlSinglePage(

            pdfUrl:String,
            pdfTitle: String,
            pdfView: PDFView,
            progressBar: ProgressBar,
            pagesTv: TextView?

        ){
            val TAG = "PDF_THUMBNNAIL_TAG"

            // Using url we can get file and its metadata from firebase storage
            val ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
            ref.getBytes(Constants.MAX_BYTES_PDF)
                .addOnSuccessListener {bytes ->

                    Log.d(TAG,"loadPdfSize : Size Bytes $bytes")

                    //SET TO PDFVIEW
                    pdfView.fromBytes(bytes)
                        .pages(0) // show first page only
                        .spacing(0)
                        .swipeHorizontal(false)
                        .enableSwipe(false)
                        .onError { t->
                            progressBar.visibility = View.INVISIBLE
                            Log.d(TAG,"loadPdfFromUrlSinglePage: ${t.message}")
                        }
                        .onPageError { page, t ->
                            progressBar.visibility = View.INVISIBLE
                            Log.d(TAG,"loadPdfFromUrlSinglePage: ${t.message}")
                        }
                        .onLoad { nbPages ->
                            //pdf loaded , we can set page count, pdf thumbnail
                            progressBar.visibility = View.INVISIBLE

                            //if pagesTv param is not null then set page numbers
                            if (pagesTv != null) {
                                pagesTv.text = "$nbPages"

                            }
                        }


                }
                .addOnFailureListener {e->
                    Log.d(TAG,"loadPdfSize: Failed to get metadata due to ${e.message}")
                }
        }

        fun loadCategory(categoryID : String, categoryTv : TextView) {

            //load category using category id from firebase
            val ref = FirebaseDatabase.getInstance().getReference("Categories")
            ref.child(categoryID)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        //get category
                        val category= "${snapshot.child("category").value}"

                        //set category
                        categoryTv.text = category

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }

    }



}