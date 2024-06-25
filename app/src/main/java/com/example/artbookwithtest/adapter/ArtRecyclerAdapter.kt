package com.example.artbookwithtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.artbookwithtest.Model.Art
import com.example.artbookwithtest.databinding.ArtRowBinding
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide: RequestManager) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>(){

        class ArtViewHolder(val binding : ArtRowBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiff = AsyncListDiffer(this,diffUtil)

    var arts: List<Art>
        get() = recyclerListDiff.currentList
        set(value) = recyclerListDiff.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val binding = ArtRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val art = arts[position]

        glide.load(art.imageUrl).into(holder.binding.artImage)
        holder.binding.artName.text = art.name
        holder.binding.artYear.text = art.year.toString()
        holder.binding.artArtist.text = art.artistName
    }
}