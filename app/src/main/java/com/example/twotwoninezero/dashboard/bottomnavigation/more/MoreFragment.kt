package com.example.twotwoninezero.dashboard.bottomnavigation.more

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.twotwoninezero.ThisApplication
import com.example.twotwoninezero.loginsignup.LoginScreenActivity
import kotlinx.android.synthetic.main.fragment_more.*

class MoreFragment :  BaseFragment() {

    var more_aboutus:LinearLayout?=null
    var more_contactus:LinearLayout?=null
    var more_logout:TextView?=null
    var morefragmentBack:ImageView?=null
    var commonContactCallMain:ImageView?=null
    override fun initViewModel() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view= inflater.inflate(R.layout.fragment_more, container, false)

        more_aboutus=view.findViewById(R.id.more_aboutus)
        more_contactus=view.findViewById(R.id.more_contactus)
        more_logout=view.findViewById(R.id.more_logout)
        morefragmentBack=view.findViewById(R.id.morefragmentBack)

        commonContactCallMain = view.findViewById(R.id.commonContactCallMain)

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        more_aboutus?.setOnClickListener {
            findNavController().navigate(
                MoreFragmentDirections.actionMorescreenFragmentToAboutUs()

            )
        }

        more_contactus?.setOnClickListener {
            findNavController().navigate(
                MoreFragmentDirections.actionMorescreenFragmentToContactUs()

            )
        }

        morefragmentBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }

        more_logout?.setOnClickListener {
            logout()
        }

        return view
    }


    private fun logout() {
        val dialogView = layoutInflater.inflate(R.layout.delete_or_activate_by_businessid, null)

       val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog?.setCancelable(false)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val textfieldsMain = dialogView.findViewById<TextView>(R.id.textfieldsMain)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val delete = dialogView.findViewById<TextView>(R.id.delete)
        val deleteText = dialogView.findViewById<TextView>(R.id.textfields)
        val deleteIcon = dialogView.findViewById<ImageView>(R.id.icons)

        textfieldsMain.text="Log Out"
        deleteText.text="Are you sure want to log out?"
        delete.text="   Yes   "
        cancel.text="    No    "
        deleteIcon.setImageResource(R.drawable.logouticon)

        cancel.setOnClickListener {

            customDialog?.dismiss()
        }

        delete.setOnClickListener {
            customDialog?.dismiss()
            ThisApplication.publicPrefs.jwtToken=""
            val i= Intent(requireActivity(), LoginScreenActivity::class.java)
            startActivity(i)
            requireActivity().finish()

        }

    }



}