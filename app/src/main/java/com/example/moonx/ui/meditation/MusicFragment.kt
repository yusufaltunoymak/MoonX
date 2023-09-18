package com.example.moonx.ui.meditation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moonx.R
import com.example.moonx.databinding.FragmentMusicBinding
import com.example.moonx.model.MusicItem
import com.example.moonx.ui.adapter.MeditationAdapter
import com.example.moonx.viewmodel.MeditationViewModel


class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MeditationAdapter

    private val viewModel: MeditationViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MeditationAdapter{
            viewModel.setSong(it)
            findNavController().navigate(R.id.action_meditationFragment_to_meditationPlayerFragment)
        }

        binding.recyclerViewMusic.adapter = adapter
        binding.recyclerViewMusic.layoutManager = GridLayoutManager(context, 2)
        adapter.submitList(viewModel.music)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}