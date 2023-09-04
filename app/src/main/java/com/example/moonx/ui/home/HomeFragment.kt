package com.example.moonx.ui.home

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.moonx.R
import com.example.moonx.databinding.FragmentHomeBinding
import com.example.moonx.model.Day
import com.example.moonx.repo.HomeRepository
import com.example.moonx.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var progressBar: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        progressBar = binding.progressBar


        observeData()
        zodiacObserveData()

        homeViewModel.zodiacSign.observe(viewLifecycleOwner) { zodiacSign ->
            binding.ariesTv.text = zodiacSign
        }

        binding.apply {
            horoscopeBackGround.setOnClickListener {
                generateHoroscope("normal")
            }
            cardLunarCategory.setOnClickListener {
                generateHoroscope("business")
                lunarCategoryIv.setImageResource(R.drawable.img_business2)
            }
            cardLunarCategoryFood.setOnClickListener {
                generateHoroscope("food")
                lunarCategoryIv.setImageResource(R.drawable.img_food)
            }
            cardLunarCategoryRelations.setOnClickListener {
                generateHoroscope("relations")
                lunarCategoryIv.setImageResource(R.drawable.img_relations)
            }
        }

        homeViewModel.loadingStatus.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeData() {
        val city = getSharedPrefCity()

        println(city)
        if (city.isNotEmpty()) {
            // bugünün tarihi
            val defaultCalendar = Calendar.getInstance()
            val defaultFormattedDate = SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(defaultCalendar.time)
            homeViewModel.getData(city, defaultFormattedDate, arrayListOf(), 0, "days")

            homeViewModel.selectedDate.observe(viewLifecycleOwner) { selectedCalendar ->
                val dateFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())
                val apiFormattedDate = dateFormat.format(selectedCalendar.time)

                homeViewModel.getData(city, apiFormattedDate, arrayListOf(), 0, "days")

                val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(selectedCalendar.time)
                binding.openCalendarButton.text = formattedDate
            }

            homeViewModel.observeData.observe(viewLifecycleOwner) { weatherResponse ->
                binding.apply {
                    cityTv.text = "${weatherResponse.resolvedAddress}"
                    moonRiseTv.text = "${weatherResponse.days?.first()?.moonrise}"
                    moonSetTv.text = "${weatherResponse.days?.first()?.moonset}"
                    sunRiseTv.text = "${weatherResponse.days?.first()?.sunrise}"
                    sunSetTv.text = "${weatherResponse.days?.first()?.sunset}"
                    weatherTv.text = "${weatherResponse.days?.first()?.temp} °C"
                    dateTv.text = "${weatherResponse.days?.first()?.datetime}"
                    println(weatherResponse.days)
                }
            }

            binding.openCalendarButton.setOnClickListener {
                homeViewModel.showDatePickerDialog(requireContext()) { selectedCalendar ->
                    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedCalendar.time)
                    binding.openCalendarButton.text = formattedDate
                    val apiFormattedDate = SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(selectedCalendar.time)
                    homeViewModel.getData(city, apiFormattedDate, arrayListOf(), 0, "days")
                }
            }
        }
    }

    private fun zodiacObserveData() {
        val birthDate = getSharedPrefDate()
        if (birthDate.isNotEmpty()) {
            homeViewModel.calculateZodiacSign(birthDate)
        }
    }

    private fun getSharedPrefCity(): String {
        val sharedPrefs = requireContext().getSharedPreferences("MyPrefsCity", Context.MODE_PRIVATE)
        val city = sharedPrefs.getString("birthPlace", "") ?: ""
        Log.e("city", city)
        return city
    }

    private fun getSharedPrefDate(): String {
        val sharedPrefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val birthDate = sharedPrefs.getString("birthDate", "") ?: ""
        Log.e("birth", birthDate)

        return birthDate
    }

    private fun generateHoroscope(category : String) {
        homeViewModel.generateHoroscope(category)

        homeViewModel.horoscope.observe(viewLifecycleOwner) { horoscopeText ->
            when (category) {
                "normal" ->binding.todayTv.text = horoscopeText
                "business" -> binding.lunarResponseCategoryTv.text = horoscopeText
                "food" -> binding.lunarResponseCategoryTv.text = horoscopeText
                "relations" -> binding.lunarResponseCategoryTv.text = horoscopeText

            }
        }

    }
}
