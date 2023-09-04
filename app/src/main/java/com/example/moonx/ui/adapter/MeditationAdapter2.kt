package com.example.moonx.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moonx.databinding.GridMeditation2RowBinding

class MeditationAdapter2 : RecyclerView.Adapter<MeditationAdapter2.Meditation2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Meditation2ViewHolder {
        val binding = GridMeditation2RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Meditation2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Meditation2ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class Meditation2ViewHolder(private val binding: GridMeditation2RowBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}
