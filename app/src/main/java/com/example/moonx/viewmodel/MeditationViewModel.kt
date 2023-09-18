package com.example.moonx.viewmodel

import MeditationHomeFragment
import android.media.MediaPlayer
import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moonx.databinding.FragmentMeditationPlayerBinding
import com.example.moonx.datasource.MeditationDataSource
import com.example.moonx.datasource.MeditationDataSource2
import com.example.moonx.datasource.MusicDataSource
import com.example.moonx.datasource.YogaDataSource
import com.example.moonx.model.MusicItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MeditationViewModel : ViewModel() {

    private val meditationDataSource = MeditationDataSource()
    private val meditationDataSource2 = MeditationDataSource2()
    private val musicDataSource = MusicDataSource()
    private val yogaDataSource = YogaDataSource()



    private val _meditation = meditationDataSource.loadFengShui()
    val meditation: List<MusicItem> = _meditation

    private val _meditation2 = meditationDataSource2.loadXyz()
    val meditation2: List<MusicItem> = _meditation2

    private val _music = musicDataSource.loadMusicDataSource()
    val music: List<MusicItem> = _music

    private val _yoga = yogaDataSource.loadYogaDataSource()
    val yoga: List<MusicItem> = _yoga

    private val _isMusicPlaying = MutableLiveData<Boolean>()
    val isMusicPlaying: MutableLiveData<Boolean> = _isMusicPlaying

    private val _selectedSong = MutableLiveData<MusicItem?>()
    val selectedSong: LiveData<MusicItem?> = _selectedSong

    private var update: List<MusicItem>
    private var currentIndex: Int = -1

    init {
        update = when(selectedSong.value?.genre) {
            "meditation" -> meditation
            "meditation2" -> meditation2
            "music" -> music
            "yoga" -> yoga
            else -> emptyList()
        }
    }

    fun setSong(song: MusicItem) {
        _selectedSong.value = song
        updateAList()
    }

    private fun updateAList() {
        update = when(selectedSong.value?.genre) {
            "meditation" -> meditation
            "meditation2" -> meditation2
            "music" -> music
            "yoga" -> yoga
            else -> emptyList()
        }
        currentIndex = update.indexOf(selectedSong.value)
    }

    fun getNextSong(): MusicItem? {
        if (update.isEmpty()) {
            return null
        }
        currentIndex = (currentIndex + 1) % update.size
        return update[currentIndex]
    }

    fun getPreviousSong(): MusicItem? {
        if (update.isEmpty()) {
            return null
        }

        if (currentIndex == 0) {
            currentIndex = update.size - 1
        } else {
            currentIndex--
        }

        return update[currentIndex]
    }




    fun setIsMusicPlaying(isPlaying: Boolean) {
        _isMusicPlaying.value = isPlaying
    }



    private var mediaPlayerJob: Job? = null

    fun startUpdatingSeekBar(mediaPlayer:MediaPlayer, seekBar: SeekBar, binding:FragmentMeditationPlayerBinding) {
        mediaPlayerJob?.cancel()
        mediaPlayerJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                try {
                    if (mediaPlayer.isPlaying) {
                        val currentPosition = mediaPlayer.currentPosition
                        seekBar.progress = currentPosition
                        binding.startTime.text = formatDuration(currentPosition)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
                delay(1000)
            }
        }
    }

    fun formatDuration(duration: Int): String {
        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerJob?.cancel()
    }
}





