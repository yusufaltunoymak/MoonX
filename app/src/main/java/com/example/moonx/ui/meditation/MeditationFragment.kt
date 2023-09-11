package com.example.moonx.ui.meditation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.moonx.R
import com.example.moonx.databinding.FragmentMeditationBinding
import com.example.moonx.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class MeditationFragment : Fragment() {
    private var _binding: FragmentMeditationBinding? = null
    private val binding get() = _binding!!
    private val tabHeaderList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMeditationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        tabHeaderList.add("Meditation")
        tabHeaderList.add("Yoga")
        tabHeaderList.add("Music")

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabHeaderList[position]
        }.attach()

        for(i in 0..3) {
            val textView = LayoutInflater.from(requireContext()).inflate(R.layout.tab_title,null) as TextView
            tabLayout.getTabAt(i)?.customView = textView
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}