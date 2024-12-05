package com.dicoding.planetkuapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.planetkuapp.databinding.ActivityMainBinding
import com.dicoding.planetkuapp.ui.article.ArticleFragment
import com.dicoding.planetkuapp.ui.maps.MapsFragment
import com.dicoding.planetkuapp.ui.more.MoreFragment
import com.dicoding.planetkuapp.ui.home.HomeFragment
import com.dicoding.planetkuapp.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("IS_FIRST_LAUNCH", true)

        if (isFirstLaunch) {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> loadFragment(HomeFragment())
                R.id.menu_map -> loadFragment(MapsFragment())
                R.id.menu_article -> loadFragment(ArticleFragment())
                R.id.menu_more -> loadFragment(MoreFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}

