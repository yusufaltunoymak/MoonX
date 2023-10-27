package com.example.moonx.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moonx.databinding.GirdMeditationRowBinding
import com.example.moonx.model.MusicItem
import com.example.moonx.viewmodel.MeditationViewModel

class MeditationAdapter(private val onItemClicked: (MusicItem) -> Unit) : ListAdapter<MusicItem, MeditationAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GirdMeditationRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, viewModel = MeditationViewModel())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSong = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(currentSong)
        }
        holder.bind(currentSong)

    }

    class ViewHolder(private val binding: GirdMeditationRowBinding, private val viewModel: MeditationViewModel) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(musicItem: MusicItem) {
            binding.apply {
                meditationIv.setImageResource(musicItem.albumCover)
               meditationName.text = musicItem.artist
               meditationSongName.text = musicItem.songTitle
               meditationSongTime.text = musicItem.duration
            }

            binding.btnPlay.setOnClickListener {
                MusicPlayerManager.startMusic(musicItem.musicUrl)
                viewModel.isMusicPlaying.value = true

                binding.btnPlay.visibility = View.INVISIBLE
                binding.btnStop.visibility = View.VISIBLE
            }

            binding.btnStop.setOnClickListener {
                MusicPlayerManager.stopMusic()
                viewModel.isMusicPlaying.value = false
                binding.btnPlay.visibility = View.VISIBLE
                binding.btnStop.visibility = View.INVISIBLE
            }

        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<MusicItem>() {
    override fun areItemsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
        return oldItem.songTitle == newItem.songTitle
    }

    override fun areContentsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
        return oldItem == newItem
    }
}