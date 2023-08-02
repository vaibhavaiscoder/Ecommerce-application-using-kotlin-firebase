package com.example.ecomvb.activity

import android.R
import android.R.menu
import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomvb.adapter.SeachAdapter
import com.example.ecomvb.databinding.ActivitySearch2Binding
import com.example.ecomvb.model.ProductModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList


private const val TAG: String = "FIRESTORE_SEARCH_LOG"

class SearchActivity2 : AppCompatActivity() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var binding: ActivitySearch2Binding

    private var searchList: List<ProductModel> = ArrayList()
    private var searchListAdapter2 = SeachAdapter(searchList)

    private lateinit var tempArrayList: ArrayList<ProductModel>
    private lateinit var list: ArrayList<ProductModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearch2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.productSearchRecycler.hasFixedSize()
        binding.productSearchRecycler.layoutManager = LinearLayoutManager(this)
        binding.productSearchRecycler.adapter = searchListAdapter2

        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText: String = binding.searchField.text.toString()
                searchInFirestore(searchText.toLowerCase())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        list = arrayListOf<ProductModel>()
        tempArrayList = arrayListOf<ProductModel>()

//        getData()

    }

    private fun searchInFirestore(searchText: String) {
        firebaseFirestore.collection("products").orderBy("productName")
            .startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener {
                if (it.isSuccessful){
//                    get the list and set it to adapter
                    searchList = it.result!!.toObjects(ProductModel::class.java)
                    searchListAdapter2.searchList = searchList
                    searchListAdapter2.notifyDataSetChanged()
                }else{
                    Log.d(ContentValues.TAG,"Error:${it.exception!!.message}")
                }
            }

    }


//    private fun getData() {
////        val list = ArrayList<ProductModel>()
//        Firebase.firestore.collection("products")
//            .get().addOnSuccessListener {
//                list.clear()
//                for (doc in it.documents) {
//                    val data = doc.toObject(ProductModel::class.java)
//                    list.add(data!!)
//                }
//                binding.productSearchRecycler.adapter = ProductAdapter2(this, list)
//            }
//        tempArrayList.addAll(list)
//    }

}