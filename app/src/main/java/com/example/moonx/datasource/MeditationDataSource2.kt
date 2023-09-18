package com.example.moonx.datasource

import com.example.moonx.R
import com.example.moonx.model.MusicItem

class MeditationDataSource2 {
    fun loadXyz(): List<MusicItem> {
        return listOf(
            MusicItem(
                R.drawable.img_meditation2,
                R.drawable.img_background1,
                "Sanatçı3",
                "Şarkı3",
                "0:16",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "meditation2"
            ),
            MusicItem(
                R.drawable.img_meditation2,
                R.drawable.img_background1,
                "Sanatçı4",
                "Şarkı4",
                "0:16",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "meditation2"
            )
        )
    }
}