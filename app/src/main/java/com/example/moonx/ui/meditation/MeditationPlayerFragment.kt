package com.example.moonx.ui.meditation

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moonx.MainActivity
import com.example.moonx.R
import com.example.moonx.databinding.FragmentMeditationPlayerBinding
import com.example.moonx.model.MusicItem
import com.example.moonx.ui.adapter.MeditationAdapter
import com.example.moonx.viewmodel.MeditationViewModel


class MeditationPlayerFragment() : Fragment() {

    private val meditationViewModel: MeditationViewModel by viewModels()
    private lateinit var binding: FragmentMeditationPlayerBinding
    private lateinit var mediaPlayer : MediaPlayer



    private val args : MeditationPlayerFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMeditationPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    var bool=true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.arrowIv.setOnClickListener {
            val action = MeditationPlayerFragmentDirections.actionMeditationPlayerFragmentToMeditationFragment()
            findNavController().navigate(action)

        }

        binding.btnNextPlaylist.setOnClickListener {

        }


        binding.songName.text=args.media.songTitle
        binding.backgroundImage.setImageResource(args.media.albumCover)

        binding.btnPlayPlaylist.setOnClickListener {
            meditationViewModel.isMusicPlaying.value=true
            binding.btnPlayPlaylist.visibility=View.INVISIBLE
            binding.btnStopPlay.visibility=View.VISIBLE
        }
        binding.btnStopPlay.setOnClickListener {
            meditationViewModel.isMusicPlaying.value=false
            binding.btnPlayPlaylist.visibility=View.VISIBLE
            binding.btnStopPlay.visibility=View.INVISIBLE
        }

        binding.btnRefresh.setOnClickListener {
            stopMedia()
            startMedia()
            binding.horizontalProgressBar.progress=0
            binding.startTime.text="00.00"
        }

        meditationViewModel.getIsMusicPlaying().observe(viewLifecycleOwner) { isPlaying ->
            if (isPlaying) {
                if (bool){
                    startMedia()
                    bool=false
                }else{
                    resumeMedia()
                }
            }
            else {
                pauseMedia()
            }
        }
    }

    private var isPlaying = false
    private var playbackPosition=0
    private fun startMedia() {
        stopMedia()
        if (!isPlaying) {
            mediaPlayer = MediaPlayer()
            mediaPlayer.apply {
                try {
                    setDataSource(args.media.musicUrl)
                    prepareAsync()
                    setOnPreparedListener {
                        it.seekTo(playbackPosition)
                        it.start()
                        this@MeditationPlayerFragment.isPlaying = true
                        playbackPosition = 0
                        val duration = it.duration
                        binding.endTime.text = meditationViewModel.formatDuration(duration)
                        binding.horizontalProgressBar.max = duration
                        meditationViewModel.startUpdatingSeekBar(mediaPlayer, binding.horizontalProgressBar, binding)
                    }
                    setOnCompletionListener {
                        this@MeditationPlayerFragment.isPlaying = false
                        stopMedia()
                        playbackPosition = 0
                        binding.btnPlayPlaylist.visibility = View.VISIBLE
                        binding.btnStopPlay.visibility = View.INVISIBLE
                        binding.horizontalProgressBar.progress = 0
                        binding.startTime.text="00.00"
                        bool=true
                        binding.horizontalProgressBar.progress = 0
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun pauseMedia() {
        if (isPlaying) {
            mediaPlayer.pause()
            playbackPosition = mediaPlayer.currentPosition
            isPlaying = true
        }
    }

    private fun resumeMedia() {
        if (isPlaying) {
            mediaPlayer.seekTo(playbackPosition)
            mediaPlayer.start()
            isPlaying = true
            meditationViewModel.startUpdatingSeekBar(mediaPlayer, binding.horizontalProgressBar, binding)
        }
    }
    private fun stopMedia() {
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                isPlaying=false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}