package com.example.twotwoninezero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contact_us.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val strDate = "2022-06-30"

        //current date format
        val dateFormaqt = SimpleDateFormat("yyyy-MM-dd");

        val objDate = dateFormaqt.parse(strDate);

        //Expected date format
        val dateFormat2 =  SimpleDateFormat("dd-MM-yyyy");

        val finalDate = dateFormat2.format(objDate);

        println("dateFormaqt  "+finalDate)

    }
}