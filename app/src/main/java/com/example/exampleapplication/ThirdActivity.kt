package com.example.exampleapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ThirdActivity : AppCompatActivity(), AppStateListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        // add AppStateListener automatically when move to top activity
    }

    override fun enterForeground() {
        Log.d("debug", "ThirdActivity enterForeground")
    }

    override fun enterBackground() {
        Log.d("debug", "ThirdActivity enterBackground")
    }
}