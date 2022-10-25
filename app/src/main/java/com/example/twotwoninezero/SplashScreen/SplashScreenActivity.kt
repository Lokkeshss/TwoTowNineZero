package com.example.twotwoninezero.SplashScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.twotwoninezero.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed(Runnable {
            startActivity(OnBoardingScreenActivity.getStartIntent(this))
            finish()
        }, 3000)
    }
}