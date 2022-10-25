package com.example.twotwoninezero.SplashScreen.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.twotwoninezero.R
import com.example.twotwoninezero.SplashScreen.OnBoardingScreenActivity.Companion.viewPager
import com.example.twotwoninezero.SplashScreen.SplashScreenActivity
import com.example.twotwoninezero.dashboard.DashBoardActivity
import com.example.twotwoninezero.loginsignup.LoginScreenActivity

class OnBoardingScreenAdapter(var mContext: Context) :PagerAdapter(){

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.on_boarding_slidescreen, container, false)
        val onBoardLogo = view.findViewById<ImageView>(R.id.onBoardLogo)
        val obBoardTitle = view.findViewById<TextView>(R.id.obBoardTitle)
        val onBoardDes = view.findViewById<TextView>(R.id.onBoardDes)
        val onBoardIndicater = view.findViewById<ImageView>(R.id.onBoardIndicater)
        val onBoardSkip = view.findViewById<TextView>(R.id.onBoardSkip)
        val onBoardNext = view.findViewById<TextView>(R.id.onBoardNext)
        val onBoardGetStart = view.findViewById<Button>(R.id.onBoardGetStart)
        onBoardSkip.setOnClickListener {
            val intent = Intent(mContext, LoginScreenActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(intent)
        }
        onBoardGetStart.setOnClickListener {
            val intent = Intent(mContext, LoginScreenActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(intent)
        }
        onBoardNext.setOnClickListener {
            viewPager?.currentItem=position+1
        }
        when (position) {
            0 -> {
                onBoardLogo.setImageResource(R.drawable.onboard_one)
                obBoardTitle.text = "convienent"
                onBoardDes.text = "E-File Form 2290 and pay your Heavy Vehicle Truck Tax today"
                onBoardIndicater.setImageResource(R.drawable.dot_one)
                onBoardNext.visibility=View.VISIBLE
                onBoardSkip.visibility=View.VISIBLE
                onBoardGetStart.visibility=View.INVISIBLE
            }
            1 -> {
                onBoardLogo.setImageResource(R.drawable.onboard_two)
                obBoardTitle.text = "quick"
                onBoardDes.text = "Get your IRS Watermarked Schedule 1 in \\njust Two Minutes!"
                onBoardIndicater.setImageResource(R.drawable.dot_one)
                onBoardGetStart.visibility=View.INVISIBLE
                onBoardNext.visibility=View.VISIBLE
                onBoardSkip.visibility=View.VISIBLE
            }
            2 -> {
                onBoardLogo.setImageResource(R.drawable.onboard_three)
                obBoardTitle.text = "Trust"
                onBoardDes.text = "100% secure IRS Authorized E-File provider"
                onBoardIndicater.setImageResource(R.drawable.dot_one)
                onBoardGetStart.visibility=View.VISIBLE
                onBoardNext.visibility=View.INVISIBLE
                onBoardSkip.visibility=View.INVISIBLE
            }
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}