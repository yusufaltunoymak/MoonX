package com.example.moonx.viewmodel

import android.media.MediaPlayer
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moonx.databinding.FragmentMeditationPlayerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MeditationViewModel : ViewModel() {

    val isMusicPlaying = MutableLiveData<Boolean>()

    private var mediaPlayerJob: Job? = null


    fun getIsMusicPlaying(): LiveData<Boolean> {
        return isMusicPlaying
        print("a")
    }



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





