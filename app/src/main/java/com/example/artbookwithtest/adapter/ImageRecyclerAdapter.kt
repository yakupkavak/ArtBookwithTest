package com.example.artbookwithtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.example.artbookwithtest.Model.Art
import com.example.artbookwithtest.databinding.ImageRowBinding
import okhttp3.internal.Util
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide: RequestManager)
    : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {

    class ImageViewHolder(val binding : ImageRowBinding) : ViewHolder(binding.root)



    private var onItemClickListener : ((String) -> Unit) ?= null


    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiff = AsyncListDiffer(this,diffUtil)

    var images: List<String>
        get() = recyclerListDiff.currentList
        set(value) = recyclerListDiff.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setOnItemClickerListener(listener: (String) -> Unit){
        onItemClickListener = listener
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = images[position]

        glide.load(url).into(holder.binding.imageViewInrow)
        holder.binding.imageViewInrow.setOnClickListener {
            setOnItemClickerListener {
                onItemClickListener?.let {
                    it(url)
                }
            }
        }


    }
}