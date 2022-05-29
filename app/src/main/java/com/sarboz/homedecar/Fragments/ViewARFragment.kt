package com.sarboz.homedecar.Fragments

import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.lifecycleScope
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.google.gson.Gson
import com.gorisse.thomas.sceneform.scene.await
import com.sarboz.homedecar.Models.Product
import com.sarboz.homedecar.R

class ViewARFragment : Fragment(R.layout.fragment_view_a_r) {

    private lateinit var arFragment: ArFragment
    private val arSceneView get() = arFragment.arSceneView
    private val scene get() = arSceneView.scene
    private lateinit var modelUrl: String
    private var model: Renderable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gson = Gson()
        val productJson = arguments?.getString("product")
        if (productJson != null) {
            val product = gson.fromJson(productJson, Product::class.java)
            modelUrl = if (product.modelUrl != null)
                product?.modelUrl!!
            else
                "models/stol.glb";
        }
        (activity as AppCompatActivity?)!!.getSupportActionBar()!!.hide()

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val bundle = Bundle()
                    bundle.putString("product", productJson)
                    requireActivity().supportFragmentManager.commitNow {
                        replace(R.id.containerFragment, ItemDetailFragment::class.java, bundle)
                    }
                }
            })

        arFragment = (childFragmentManager.findFragmentById(R.id.arFragment) as ArFragment).apply {
            setOnSessionConfigurationListener { session, config ->
                // Modify the AR session configuration here
            }
            setOnViewCreatedListener { arSceneView ->
                arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL)
            }
            setOnTapArPlaneListener(::onTapPlane)

        }

        lifecycleScope.launchWhenCreated {
            loadModels()
        }
    }

    private suspend fun loadModels() {
        model = ModelRenderable.builder()
            .setSource(context, Uri.parse(modelUrl))
            .setIsFilamentGltf(true)
            .await()
    }

    private fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        if (model == null) {
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the Anchor.
        scene.addChild(AnchorNode(hitResult.createAnchor()).apply {
            // Create the transformable model and add it to the anchor.
            addChild(TransformableNode(arFragment.transformationSystem).apply {
                renderable = model
                renderableInstance.setCulling(false)
                renderableInstance.animate(true).start()
                // Add the View
                addChild(Node().apply {
                    // Define the relative position
                    localPosition = Vector3(0.0f, 1f, 0.0f)
                    localScale = Vector3(0.7f, 0.7f, 0.7f)
                })
            })
        })
    }

    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity?)!!.getSupportActionBar()!!.show()
    }
}