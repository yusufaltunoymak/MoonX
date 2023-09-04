package com.example.moonx.ui.onboarding

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moonx.R
import com.example.moonx.databinding.FragmentBirthBinding
import com.example.moonx.databinding.FragmentWelcomeBinding
import com.example.moonx.room.Birth
import com.example.moonx.viewmodel.onboardingviewmodel.BirthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

class BirthFragment : Fragment() {
    private lateinit var binding: FragmentBirthBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBirthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        // val birthDate = binding.birthDateEditText.text.toString()



        binding.birthDateEditText.setOnClickListener {
            showDatePicker()
        }

        binding.birthTimeEditText.setOnClickListener {
            showTimePicker()
        }

        binding.nextButton2.setOnClickListener {
            val dataBirthDate = binding.birthDateEditText.text
            val birthDate = dataBirthDate
            editor.putString("birthDate", dataBirthDate.toString())
            editor.apply()
            val action = BirthFragmentDirections.actionBirthFragmentToPlaceFragment()
            findNavController().navigate(action)

        }
    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(requireContext())
        datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = "${dayOfMonth}.${month + 1}.${year}"
            binding.birthDateEditText.setText(selectedDate)
        }
        datePicker.show()
    }

    private fun showTimePicker() {
        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val selectedTime = "${hourOfDay}:${minute}"
                binding.birthTimeEditText.setText(selectedTime)
            },
            0,
            0,
            true
        )
        timePicker.show()
    }

}