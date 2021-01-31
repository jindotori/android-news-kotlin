package com.dotori.news.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dotori.news.ui.main.fragment.*


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class CategoriesPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private var fragmentList = ArrayList<Fragment>()

    init {
        fragmentList.apply {
            add(HomeFragment())
            add(BusinessFragment())
            add(SocietyFragment())
            add(ScienceFragment())
            add(SportFragment())
//            add(OpinionFragment())
        }
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}