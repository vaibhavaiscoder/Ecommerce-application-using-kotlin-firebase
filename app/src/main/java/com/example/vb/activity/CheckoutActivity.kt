package com.example.ecomvb.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ecomvb.R
import com.example.ecomvb.roomdb.AppDatabase
import com.example.ecomvb.roomdb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject


class CheckoutActivity : AppCompatActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_1JEP5wNoSn3dS0");

        val price = intent.getStringExtra("totalCost")
        try {
            val options = JSONObject()
            options.put("name", "K-STAR")
            options.put("description", "E-Commerce")
            options.put(
                "image",
                "data:image/png;base64,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"
            )
//            options.put("order_id", "order_DBJOWzybf0sJbb") //from response of step 3.
            options.put("theme.color", "#7E42C7")
            options.put("currency", "INR")
            options.put("amount", (price!!.toInt()* 100)) //pass amount in currency subunits
            options.put("prefill.email", "vaibhavghugase00@gmail.com")
            options.put("prefill.contact", "7020210480")
//            val retryObj = JSONObject()
//            retryObj.put("enabled", true)
//            retryObj.put("max_count", 4)
//            options.put("retry", retryObj)
            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()

        uploadData()
    }

    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")
        for (currentId in id!!) {
            fetchData(currentId)
        }
    }

    private fun fetchData(productId: String?) {

        val dao = AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    dao.deleteProduct(ProductModel(productId))
                }
                savedData(
                    it.getString("productName"),
                    it.getString("productSp"),
                    it.getString("productCoverImg"),
                    productId
                )
            }
    }

    private fun savedData(name: String?, price: String?, productCoverImg:String?, productID: String) {

        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val data = hashMapOf<String, Any>()
        data["name"] = name!!
        data["price"] = price!!
        data["productCoverImg"] = productCoverImg!!
        data["productId"] = productID
        data["status"] = "Ordered"
        data["userId"] = preferences.getString("number", "")!!

        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderId"] = key

        firestore.document(key).set(data).addOnSuccessListener {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
    }
}