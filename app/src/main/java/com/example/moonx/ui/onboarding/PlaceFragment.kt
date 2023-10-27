package com.example.moonx.ui.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moonx.databinding.FragmentPlaceBinding
import dagger.hilt.android.AndroidEntryPoint

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
            val action = PlaceFragmentDirections.actionPlaceFragmentToInAppFragment()
            findNavController().navigate(action)
        }
    }
}







