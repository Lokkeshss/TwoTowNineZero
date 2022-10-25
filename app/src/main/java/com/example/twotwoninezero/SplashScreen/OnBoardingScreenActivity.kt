package com.example.twotwoninezero.SplashScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.twotwoninezero.R
import com.example.twotwoninezero.SplashScreen.adapter.OnBoardingScreenAdapter

class OnBoardingScreenActivity : AppCompatActivity() {
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, OnBoardingScreenActivity::class.java)
        }
        var viewPager:ViewPager?=null
    }


    var mOnBoardingScreenAdapter: OnBoardingScreenAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_screen)

        viewPager=findViewById(R.id.viewPager)
        mOnBoardingScreenAdapter = OnBoardingScreenAdapter(this)
        viewPager?.adapter=mOnBoardingScreenAdapter

    }
}