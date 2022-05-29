package com.sarboz.homedecar.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.commitNow
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.sarboz.homedecar.Models.Product
import com.sarboz.homedecar.R
import pl.droidsonroids.gif.GifImageView

class ItemDetailFragment : Fragment() {

    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gson = Gson()
        val view = inflater.inflate(R.layout.fragment_item_detail, container, false)
        val productJson = arguments?.getString("product")

        val price = view.findViewById<TextView>(R.id.price)
        val name = view.findViewById<TextView>(R.id.name)
        val detail = view.findViewById<TextView>(R.id.detail)
        val imageView = view.findViewById<GifImageView>(R.id.imageView)
        val fab = view.findViewById<FloatingActionButton>(R.id.viewArFab)

        if (productJson != null) {
            product = gson.fromJson(productJson, Product::class.java)
            activity?.title = product.name
            price.text = product.price
            name.text = product.name
            detail.text = product.detail

            Glide.with(requireContext())
                .load(product.pictureUrl)
                .into(imageView)
            val bundle = Bundle()

            bundle.putString("product", productJson)
            fab.setOnClickListener {
                requireActivity().supportFragmentManager.commitNow {
                    replace(R.id.containerFragment, ViewARFragment::class.java, bundle)
                }
            }
        }
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().supportFragmentManager.commitNow {
                        replace(R.id.containerFragment, HomeFragment::class.java, Bundle())
                        requireActivity().setTitle(R.string.app_name)
                    }
                }
            })
        return view
    }

}