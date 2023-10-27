package com.example.moonx.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.moonx.R
import com.example.moonx.databinding.FragmentHomeBinding
import com.example.moonx.util.Constants
import com.example.moonx.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
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
                lunarCategoryIv.setImageResource(R.drawable.food_2)
            }
            cardLunarCategoryRelations.setOnClickListener {
                generateHoroscope("relations")
                lunarCategoryIv.setImageResource(R.drawable.img_relations)
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
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

        if (city.isNotEmpty()) {
            val defaultCalendar = Calendar.getInstance()
            val defaultFormattedDate =
                SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(defaultCalendar.time)

            homeViewModel.getData(city, defaultFormattedDate, Constants.dayProperties.toString(),true)

            homeViewModel.selectedDate.observe(viewLifecycleOwner) { selectedCalendar ->
                val dateFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())
                val apiFormattedDate = dateFormat.format(selectedCalendar.time)

                homeViewModel.getData(city, apiFormattedDate, Constants.dayProperties.joinToString(","),true)
                val formattedDate = SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale.getDefault()
                ).format(selectedCalendar.time)
                binding.openCalendarButton.text = formattedDate
            }

            homeViewModel.observeData.observe(viewLifecycleOwner) { weatherResponse ->
                binding.apply {
                    weatherResponse?.days?.let {
                        cityTv.text = "${weatherResponse.resolvedAddress}"
                        moonRiseTv.text = formatTime(weatherResponse.days?.first()?.moonRise)
                        moonSetTv.text = formatTime(weatherResponse.days?.first()?.moonSet)
                        sunRiseTv.text = formatTime(weatherResponse.days?.first()?.sunrise)
                        sunSetTv.text = formatTime(weatherResponse.days?.first()?.sunset)
                        weatherTv.text = "${weatherResponse.days?.first()?.temp} °C"
                        dateTv.text = formatDate(weatherResponse.days?.first()?.datetime)
                    }

                    homeViewModel.onError.observe(viewLifecycleOwner) {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        binding.openCalendarButton.setOnClickListener {
            homeViewModel.showDatePickerDialog(requireContext()) { selectedCalendar ->
                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedCalendar.time)
                binding.openCalendarButton.text = formattedDate
                val apiFormattedDate =
                    SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(selectedCalendar.time)
                homeViewModel.getData(city, apiFormattedDate, Constants.dayProperties.joinToString(","),true)
            }
        }
    }

    private fun generateHoroscope(category: String) {
        homeViewModel.generateHoroscope(category)

        homeViewModel.completionResponse.observe(viewLifecycleOwner) { response ->
            val horoscopeText = response?.trim() ?: homeViewModel.onError.value
            when (category) {
                "normal" -> binding.todayTv.text = horoscopeText
                "business", "food", "relations" -> binding.lunarResponseCategoryTv.text = horoscopeText
            }

            homeViewModel.onError.observe(viewLifecycleOwner) {
                it.let {
                    binding.todayTv.text = it
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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
        return city
    }

    private fun getSharedPrefDate(): String {
        val sharedPrefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val birthDate = sharedPrefs.getString("birthDate", "") ?: ""
        return birthDate
    }
    private fun formatTime(apiTime: String?): String {
        apiTime?.let {
            val parts = apiTime.split(":")
            if (parts.size >= 2) {
                val hour = parts[0]
                val minute = parts[1]
                return "$hour.$minute"
            }
        }
        return "No Data"
    }

    private fun formatDate(apiDate: String?): String {
        apiDate?.let {
            val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val displayDateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

            try {
                val date = apiDateFormat.parse(apiDate)
                return displayDateFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return "No Data"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

