package com.example.moonx.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moonx.databinding.GirdMeditationRowBinding
import com.example.moonx.model.MusicItem
import com.example.moonx.ui.meditation.MeditationFragmentDirections

class MeditationAdapter(private val musicList: List<MusicItem>) : RecyclerView.Adapter<MeditationAdapter.ViewHolder>() {

class ViewHolder(val binding : GirdMeditationRowBinding): RecyclerView.ViewHolder(binding.root) {

}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GirdMeditationRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = musicList[position]
        holder.binding.meditationIv.setImageResource(currentItem.albumCover)
        holder.binding.meditationName.text = currentItem.artist
        holder.binding.meditationSongName.text = currentItem.songTitle
        holder.binding.meditationSongTime.text = currentItem.duration




        holder.itemView.setOnClickListener {
            val action = MeditationFragmentDirections.actionMeditationFragmentToMeditationPlayerFragment(currentItem)
            holder.itemView.findNavController().navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return musicList.size
    }

}
