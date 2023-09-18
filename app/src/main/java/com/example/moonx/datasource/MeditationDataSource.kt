package com.example.moonx.datasource

import com.example.moonx.R
import com.example.moonx.model.MusicItem

class MeditationDataSource {
    fun loadFengShui() : List<MusicItem> {
        return listOf(
            MusicItem(
                R.drawable.img_meditation,
                R.drawable.img_background1,
                "Sanatçı1",
                "Şarkı1",
                "0:16",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "meditation"
            ),
            MusicItem(
                R.drawable.img_meditation,
                R.drawable.img_background1,
                "Sanatçı2",
                "Şarkı2",
                "0:16",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "meditation"
            ),

            )
    }

}