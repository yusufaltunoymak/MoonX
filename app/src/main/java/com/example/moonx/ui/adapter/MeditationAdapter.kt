package com.example.moonx.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moonx.databinding.GirdMeditationRowBinding

class MeditationAdapter : RecyclerView.Adapter<MeditationAdapter.MeditationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeditationViewHolder {
        val binding = GirdMeditationRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeditationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeditationViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class MeditationViewHolder(private val binding: GirdMeditationRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
