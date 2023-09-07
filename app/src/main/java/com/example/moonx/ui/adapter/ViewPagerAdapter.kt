package com.example.moonx.ui.adapter

import MeditationHomeFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moonx.ui.meditation.MusicFragment
import com.example.moonx.ui.meditation.YogaFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }



    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MeditationHomeFragment()
            1 -> YogaFragment()
            2 -> MusicFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }



}
