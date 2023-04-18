package com.example.exampleapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.exampleapplication.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity(), AppStateListener {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // add listener manually
        // receive events with top activity
//        ExApplication.setAppStateListener(this)

        binding.thirdActivityButton.setOnClickListener {
            val thirdActivityIntent = Intent(this, ThirdActivity::class.java)
            startActivity(thirdActivityIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // automatically remove listener at this point
    }

    override fun enterForeground() {
        Log.d("debug", "SecondActivity enterForeground")
    }

    override fun enterBackground() {
        Log.d("debug", "SecondActivity enterBackground")
    }
}