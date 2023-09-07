package com.example.moonx.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moonx.R
import com.example.moonx.databinding.FragmentBirthBinding
import com.example.moonx.databinding.FragmentPlaceBinding
import com.example.moonx.databinding.FragmentWelcomeBinding
import com.example.moonx.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlaceFragment : Fragment() {

    private lateinit var binding: FragmentPlaceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireContext().getSharedPreferences("MyPrefsCity", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()



        binding.nextButton3.setOnClickListener {
            val dataBirthPlace = binding.birthDateEditText.text

            val birthPlace = dataBirthPlace.toString()
            editor.putString("birthPlace", birthPlace)
            editor.apply()


        }

    }
}







