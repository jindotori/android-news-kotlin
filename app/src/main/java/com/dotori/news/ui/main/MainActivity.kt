package com.dotori.news.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dotori.news.R
import com.dotori.news.databinding.ActivityMainBinding
import com.dotori.news.ui.adapter.CategoriesPagerAdapter
import com.dotori.news.ui.main.fragment.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity() {

    private val tabTitles = arrayListOf(
        "home",
        "business",
        "society",
        "science",
        "sport",
//        "opinion"
    )
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        with(binding) {
            lifecycleOwner = this@MainActivity
            setCategoriesLayout(this)
        }
    }

    private fun setCategoriesLayout(binding: ActivityMainBinding) {
        binding.viewPager.apply {
            adapter = CategoriesPagerAdapter(this@MainActivity)
        }


        with(binding) {
            TabLayoutMediator(tabs, viewPager) {tab, position ->
                tab.text = tabTitles[position].toString()
            }
        }.attach()
    }
}