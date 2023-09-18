package com.example.moonx.ui.meditation

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.moonx.databinding.FragmentMeditationPlayerBinding
import com.example.moonx.viewmodel.MeditationViewModel

class MeditationPlayerFragment : Fragment() {

    private val meditationViewModel: MeditationViewModel by activityViewModels()
    private var _binding: FragmentMeditationPlayerBinding? = null
    private val binding get() = _binding!!
    private val mediaPlayer = MediaPlayer()
    private var isPlaying = false
    private var playbackPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMeditationPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    var bool = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        meditationViewModel.selectedSong.observe(viewLifecycleOwner) { song ->
            song?.let { song ->
                mediaPlayer.reset()
                mediaPlayer.setDataSource(song.musicUrl)
                mediaPlayer.prepareAsync()
                binding.songName.text = song.songTitle
                binding.backgroundImage.setImageResource(song.playerCover)
                binding.btnPlayPlaylist.visibility =
                    if (mediaPlayer.isPlaying) View.VISIBLE else View.INVISIBLE
                binding.btnStopPlay.visibility =
                    if (!mediaPlayer.isPlaying) View.VISIBLE else View.INVISIBLE
                startMedia()
            }
        }



        binding.btnNextPlaylist.setOnClickListener {
            val next = meditationViewModel.getNextSong()

            if (next != null) {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(next.musicUrl)
                mediaPlayer.prepare()
                binding.backgroundImage.setImageResource(next.playerCover)
                binding.songName.text = next.songTitle
                binding.btnPlayPlaylist.visibility =
                    if (mediaPlayer.isPlaying) View.VISIBLE else View.INVISIBLE
                binding.btnStopPlay.visibility =
                    if (!mediaPlayer.isPlaying) View.VISIBLE else View.INVISIBLE
                mediaPlayer.start()
            }
        }

        binding.btnBackPlaylist.setOnClickListener {
            val previous = meditationViewModel.getPreviousSong()

            if (previous != null) {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(previous.musicUrl)
                mediaPlayer.prepare()
                binding.backgroundImage.setImageResource(previous.playerCover)
                binding.songName.text = previous.songTitle
                binding.btnPlayPlaylist.visibility =
                    if (mediaPlayer.isPlaying) View.VISIBLE else View.INVISIBLE
                binding.btnStopPlay.visibility =
                    if (!mediaPlayer.isPlaying) View.VISIBLE else View.INVISIBLE
                mediaPlayer.start()
            }
        }



        meditationViewModel.isMusicPlaying.observe(viewLifecycleOwner) { isPlaying ->
            if (isPlaying) {
                if (bool) {
                    startMedia()
                    bool = false
                } else {
                    resumeMedia()
                }
            }
            else {
                pauseMedia()
            }
        }

        binding.apply {
            arrowIv.setOnClickListener {
                val action =
                    MeditationPlayerFragmentDirections.actionMeditationPlayerFragmentToMeditationFragment()
                findNavController().navigate(action)
            }



            btnPlayPlaylist.setOnClickListener {
                if (isPlaying) {

                } else {
                    if (playbackPosition > 0) {
                        mediaPlayer.seekTo(playbackPosition)
                        mediaPlayer.start()
                    } else {
                        startMedia()
                    }
                    meditationViewModel.setIsMusicPlaying(true)
                    binding.btnPlayPlaylist.visibility = View.INVISIBLE
                    binding.btnStopPlay.visibility = View.VISIBLE
                }
            }


            btnStopPlay.setOnClickListener {
                if (isPlaying) {
                    mediaPlayer.pause()
                    playbackPosition = mediaPlayer.currentPosition
                    isPlaying = false
                    meditationViewModel.setIsMusicPlaying(false)
                    btnPlayPlaylist.visibility = View.VISIBLE
                    btnStopPlay.visibility = View.INVISIBLE
                }
            }

            btnRefresh.setOnClickListener {
                if(mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    mediaPlayer.seekTo(0)
                    mediaPlayer.start()
                }
                horizontalProgressBar.progress = 0
                startTime.text = "00.00"
            }
        }
    }

    private fun startMedia() {

        if (!isPlaying) {
            try {

                mediaPlayer.apply {
                    setOnPreparedListener {
                        it.start()
                        this@MeditationPlayerFragment.isPlaying = true
                        val duration = it.duration
                        binding.endTime.text = meditationViewModel.formatDuration(duration)
                        binding.horizontalProgressBar.max = duration
                        meditationViewModel.startUpdatingSeekBar(
                            mediaPlayer,
                            binding.horizontalProgressBar,
                            binding
                        )
                    }
                    setOnCompletionListener {
                        this@MeditationPlayerFragment.isPlaying = false
                        playbackPosition = 0
                        binding.btnPlayPlaylist.visibility = View.VISIBLE
                        binding.btnStopPlay.visibility = View.INVISIBLE
                        binding.horizontalProgressBar.progress = 0
                        binding.startTime.text = "00.00"
                        bool = true
                        binding.horizontalProgressBar.progress = 0
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }




    private fun pauseMedia() {
        if (isPlaying) {
            mediaPlayer.pause()
            playbackPosition = mediaPlayer.currentPosition
            isPlaying = false
        }
    }

    private fun resumeMedia() {
        if (!isPlaying) {
            mediaPlayer.seekTo(playbackPosition)
            mediaPlayer.start()
            isPlaying = true
            meditationViewModel.startUpdatingSeekBar(
                mediaPlayer,
                binding.horizontalProgressBar,
                binding
            )
        }
    }


    private fun stopMedia() {
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                isPlaying = false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopMedia()
        _binding = null
    }
}