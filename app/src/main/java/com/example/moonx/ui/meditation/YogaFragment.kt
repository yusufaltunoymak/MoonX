package com.example.moonx.ui.meditation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moonx.R
import com.example.moonx.databinding.FragmentMeditationHomeBinding
import com.example.moonx.databinding.FragmentYogaBinding
import com.example.moonx.model.MusicItem
import com.example.moonx.ui.adapter.MeditationAdapter
import com.example.moonx.ui.adapter.MeditationAdapter2
import com.example.moonx.viewmodel.MeditationViewModel


class YogaFragment : Fragment() {
    private lateinit var binding: FragmentYogaBinding
    private lateinit var adapter1: MeditationAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentYogaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter1 = MeditationAdapter(
            listOf(
                MusicItem(
                    R.drawable.img_meditation,
                    "Sanatçı1",
                    "Şarkı1",
                    "3:45",
                    "https://uberduck-outputs-permalink.s3-us-west-2.amazonaws.com/22bf853e-7162-49a0-8eb6-8dd5684f9622/mix.wav"
                ),
                MusicItem(
                    R.drawable.img_meditation,
                    "Sanatçı2",
                    "Şarkı2",
                    "3:45",
                    "https://uberduck-outputs-permalink.s3-us-west-2.amazonaws.com/22bf853e-7162-49a0-8eb6-8dd5684f9622/mix.wav"
                ),

                )
        )


        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter1
    }
}
