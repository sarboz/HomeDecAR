package com.sarboz.homedecar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.ar.sceneform.samples.gltf.ViewARFragment

class MainActivity : AppCompatActivity(R.layout.activity_main)  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            add(R.id.containerFragment, ViewARFragment::class.java, Bundle())
        }
    }


}