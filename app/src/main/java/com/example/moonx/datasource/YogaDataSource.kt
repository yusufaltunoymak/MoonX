package com.example.moonx.datasource

import com.example.moonx.R
import com.example.moonx.model.MusicItem

class YogaDataSource {

    fun loadYogaDataSource() : List<MusicItem> {
        return listOf(
            MusicItem(
                R.drawable.img_meditation,
                R.drawable.img_background2,
                "Sanatçı10",
                "Şarkı10",
                "0:16",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "yoga"
            ),
            MusicItem(
                R.drawable.img_meditation,
                R.drawable.img_background2,
                "Sanatçı11",
                "Şarkı11",
                "0:16",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "yoga"
            )
        )
    }
}