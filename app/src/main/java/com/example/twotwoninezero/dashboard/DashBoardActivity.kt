package com.example.twotwoninezero.dashboard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.twotwoninezero.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.example.twotwoninezero.SplashScreen.OnBoardingScreenActivity
import com.example.twotwoninezero.databinding.ActivityDashBoardBinding

class DashBoardActivity : AppCompatActivity() {
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DashBoardActivity::class.java)
        }
    }
/*    lateinit var toggle: ActionBarDrawerToggle*/
    private lateinit var binding: ActivityDashBoardBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController=Navigation.findNavController(this,R.id.activity_main_nav_host_fragment)
        setupWithNavController(binding.bottomNavigationView,navController)

       /* binding.apply {
            toggle = ActionBarDrawerToggle(this@DashBoardActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.firstItem -> {
                        Toast.makeText(this@DashBoardActivity, "First Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.secondtItem -> {
                        Toast.makeText(this@DashBoardActivity, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.thirdItem -> {
                        Toast.makeText(this@DashBoardActivity, "third Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }

        binding.mainNavigation.setOnClickListener {

            if(!binding.drawerLayout.isDrawerOpen(Gravity.START)) binding.drawerLayout.openDrawer(Gravity.START);
            else binding.drawerLayout.closeDrawer(Gravity.END);
        }*/

    }

  /*  override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }*/
}