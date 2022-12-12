package com.example.twotwoninezero.SplashScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.Button
import com.example.twotwoninezero.R
import com.example.twotwoninezero.ThisApplication
import com.example.twotwoninezero.dashboard.DashBoardActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed(Runnable {
            if (ThisApplication.publicPrefs.jwtToken.isNullOrEmpty()){
                startActivity(OnBoardingScreenActivity.getStartIntent(this))
                finish()
            }else{
                startActivity(DashBoardActivity.getStartIntent(this))
                finish()
            }

        }, 3000)
    }

}