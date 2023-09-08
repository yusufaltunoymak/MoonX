package com.example.moonx.ui.adapter

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moonx.databinding.GirdMeditationRowBinding
import com.example.moonx.databinding.GridMeditation2RowBinding
import com.example.moonx.model.MusicItem
import com.example.moonx.ui.meditation.MeditationFragmentDirections
import com.example.moonx.viewmodel.MeditationViewModel
import javax.inject.Inject

class MeditationAdapter2 @Inject constructor( private val meditationViewModel: MeditationViewModel,
     private val musicList: List<MusicItem>) : RecyclerView.Adapter<MeditationAdapter2.ViewHolder>() {
    inner class ViewHolder(val binding : GridMeditation2RowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GridMeditation2RowBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = musicList[position]
        holder.binding.meditation2Iv.setImageResource(currentItem.albumCover)
        holder.binding.meditationName2.text = currentItem.artist
        holder.binding.meditationSongName2.text = currentItem.songTitle
        holder.binding.meditationSongTime.text = currentItem.duration




        var mediaPlayer: MediaPlayer? = null



        holder.itemView.setOnClickListener {
            val nextItemPosition = position + 1
            if (nextItemPosition < musicList.size) {
                val nextItem = musicList[nextItemPosition]
                val action = MeditationFragmentDirections.actionMeditationFragmentToMeditationPlayerFragment(currentItem, nextItem)
                holder.itemView.findNavController().navigate(action)
            }
        }


        holder.binding.btnPlay.setOnClickListener {
            holder.binding.btnPlay.visibility = View.INVISIBLE
            holder.binding.btnStop.visibility = View.VISIBLE
            meditationViewModel.isMusicPlaying.value = true

            mediaPlayer = MediaPlayer()
            mediaPlayer?.apply {
                try {
                    setDataSource(currentItem.musicUrl)
                    prepareAsync()
                    setOnPreparedListener {
                        it.start()
                    }
                    setOnCompletionListener {
                        it.reset()
                        it.release()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        holder.binding.btnStop.setOnClickListener {
            meditationViewModel.isMusicPlaying.value = false
            holder.binding.btnPlay.visibility = View.VISIBLE
            holder.binding.btnStop.visibility = View.INVISIBLE

            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                }
                reset()
                release()
            }
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}
