package com.example.covid_n3ws

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.example.covid_n3ws.fragment.AboutFragment
import com.example.covid_n3ws.fragment.DataFragment
import com.example.covid_n3ws.fragment.HomeFragment
import com.example.covid_n3ws.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        supportActionBar?.title = null

        bottom_nav.setOnTabSelectedListener { position, _ ->
            when (position) {
                0 -> {
                    val homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_id, homeFragment)
                        .commit()
                }
                1 -> {
                    val dataFragment = DataFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_id, dataFragment)
                        .commit()
                }

                2 -> {
                    val profileFragment = ProfileFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_id, profileFragment)
                        .commit()
                }

                3 -> {
                    val aboutFragment = AboutFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_id, aboutFragment)
                        .commit()
                }
            }
            return@setOnTabSelectedListener true
        }
        this.createBottomNavigation()
    }

    private fun createBottomNavigation() {
        val home = AHBottomNavigationItem(
            R.string.title_home,
            R.drawable.round_home_black_48dp,
            R.color.tab_main
        )

        val data = AHBottomNavigationItem(
            R.string.title_data,
            R.drawable.round_trending_up_black_48dp,
            R.color.tab_data
        )

        val profile = AHBottomNavigationItem(
            R.string.title_profile,
            R.drawable.round_account_circle_black_48dp,
            R.color.tab_profile
        )

        val about = AHBottomNavigationItem(
            R.string.title_about,
            R.drawable.round_announcement_black_48dp,
            R.color.tab_about
        )

        bottom_nav.addItem(home)
        bottom_nav.addItem(data)
        bottom_nav.addItem(profile)
        bottom_nav.addItem(about)

        bottom_nav.defaultBackgroundColor = Color.parseColor("#FEFEFE")
        bottom_nav.titleState = AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE

        bottom_nav.accentColor = Color.parseColor("#F63D2B")
        bottom_nav.inactiveColor = Color.parseColor("#747474")
        bottom_nav.isColored = true
        bottom_nav.currentItem = 0
    }
}
