package com.example.moonx.datasource

import com.example.moonx.R
import com.example.moonx.model.MusicItem

class MusicDataSource {
    fun loadMusicDataSource() : List<MusicItem> {
        return listOf(
            MusicItem(
                R.drawable.img_music1,
                R.drawable.img_background3,
                "Sanatçı5",
                "Şarkı5",
                "0:16",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "music"
            ),
            MusicItem(
                R.drawable.img_music2,
                R.drawable.img_background3,
                "Sanatçı6",
                "Şarkı6",
                "0:16",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "music"
            ),
            MusicItem(
                R.drawable.img_music3,
                R.drawable.img_background3,
                "Sanatçı7",
                "Şarkı7",
                "0:16",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "music"
            ),
            MusicItem(
                R.drawable.img_music4,
                R.drawable.img_background3,
                "Sanatçı8",
                "Şarkı8",
                "0:16",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
                "music"
            )
        )
    }
}