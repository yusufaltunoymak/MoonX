package com.example.moonx.ui.inapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moonx.R
import com.example.moonx.databinding.FragmentInAppBinding
import com.example.moonx.ui.onboarding.PlaceFragmentDirections

class InAppFragment : Fragment() {

    private lateinit var binding: FragmentInAppBinding
    private var isDailyCardSelected = false
    private var isWeeklyCardSelected = false
    private var isMonthlyCardSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInAppBinding.inflate(inflater, container, false)

        binding.dailyCard.setOnClickListener {
            if (!isDailyCardSelected) {
                isDailyCardSelected = true
                isWeeklyCardSelected = false
                isMonthlyCardSelected = false
                updateCardBackground(binding.dailyCard, true)
                updateCardBackground(binding.weeklyCard, false)
                updateCardBackground(binding.monthlyCard, false)
            }
        }

        binding.weeklyCard.setOnClickListener {
            if (!isWeeklyCardSelected) {
                isWeeklyCardSelected = true
                isDailyCardSelected = false
                isMonthlyCardSelected = false
                updateCardBackground(binding.dailyCard, false)
                updateCardBackground(binding.weeklyCard, true)
                updateCardBackground(binding.monthlyCard, false)
            }
        }

        binding.monthlyCard.setOnClickListener {
            if (!isMonthlyCardSelected) {
                isMonthlyCardSelected = true
                isDailyCardSelected = false
                isWeeklyCardSelected = false
                updateCardBackground(binding.dailyCard, false)
                updateCardBackground(binding.weeklyCard, false)
                updateCardBackground(binding.monthlyCard, true)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonClose.setOnClickListener {
            val action = InAppFragmentDirections.actionInAppFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateCardBackground(cardView: View, isSelected: Boolean) {
        if (isSelected) {
            cardView.setBackgroundResource(R.drawable.btn_inapppremium)
        } else {
            cardView.setBackgroundResource(R.drawable.btn_inapp)
        }
    }
}
