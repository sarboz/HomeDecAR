package com.sarboz.homedecar.Fragments

import android.os.Bundle
import android.util.JsonReader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.sarboz.homedecar.Adapters.ProductsAdapter
import com.sarboz.homedecar.Models.Product
import com.sarboz.homedecar.R
import com.sarboz.homedecar.Utils.OnItemClickListener
import org.json.JSONObject
import org.json.JSONStringer
import org.json.JSONTokener


class HomeFragment : Fragment(), OnItemClickListener {

    lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val rvList = view.findViewById<RecyclerView>(R.id.rv_list);

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference().child("products")

        val options: FirebaseRecyclerOptions<Product> = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(myRef, Product::class.java)
            .build()
        productsAdapter = ProductsAdapter(options, this)

        rvList.adapter = productsAdapter

        return view
    }

    override fun onStart() {
        super.onStart()
        productsAdapter.startListening()
    }


    override fun onStop() {
        super.onStop()
        productsAdapter.stopListening()
    }

    override fun OnItemClickListener(product: Product) {
        val gson = Gson()
        val bundle = Bundle()
        bundle.putString("product", gson.toJson(product))
        requireActivity().supportFragmentManager.commitNow {
            replace(R.id.containerFragment, ItemDetailFragment::class.java, bundle)
        }
    }
}