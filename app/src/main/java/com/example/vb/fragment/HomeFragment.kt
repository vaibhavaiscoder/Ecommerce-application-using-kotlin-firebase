package com.example.ecomvb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.ecomvb.R
import com.example.ecomvb.adapter.CategoryAdapter
import com.example.ecomvb.adapter.ProductAdapter
import com.example.ecomvb.databinding.FragmentHomeBinding
import com.example.ecomvb.model.AddProductModel
import com.example.ecomvb.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)


//        binding.button1.setOnClickListener{
//            val intent = Intent(requireContext(),ProductDetailsActivity::class.java)
//            startActivity(intent)
//        }
        val preference =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)

        if (preference.getBoolean("isCart", false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

        getCategories()
        getProducts()
        getSliderImage()
        getBannerImage()
        return binding.root
    }

//    private fun stopShimmerEffect() {
//        binding.shimmerViewContainer.visibility = View.GONE
//        if (binding.shimmerViewContainer.isShimmerStarted) {
//            binding.shimmerViewContainer.stopShimmer()
//        }
//    }

    private fun getBannerImage() {
        Firebase.firestore.collection("banner").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.bannerImage)
            }
    }
    private fun getSliderImage() {
        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener {
                val list = it.get("listImages") as ArrayList<String>
//                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
//                stopShimmerEffect()
                val slideList = ArrayList<SlideModel>()
                for (data in list) {
                    slideList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
                }
                binding.imageSlider.setImageList(slideList)
            }
    }
//    private fun getSliderImage() {
//        Firebase.firestore.collection("slider").document("item")
//            .get().addOnSuccessListener {
//                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
////                stopShimmerEffect()
//            }
//    }

    private fun getProducts() {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                binding.productRecycler.adapter = ProductAdapter(requireContext(), list)
                binding.productRecycler.setHasFixedSize(true)
                binding.productRecycler.layoutManager = GridLayoutManager(requireContext(),2)

            }
    }


    private fun getCategories() {
        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRecycler.adapter = CategoryAdapter(requireContext(), list)
            }
    }

}