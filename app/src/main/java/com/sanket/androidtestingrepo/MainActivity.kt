package com.sanket.androidtestingrepo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanket.androidtestingrepo.espresso.EspressoIdlingResource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        EspressoIdlingResource.decrement()
    }
}