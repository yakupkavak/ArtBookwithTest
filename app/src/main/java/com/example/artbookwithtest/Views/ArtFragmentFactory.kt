package com.example.artbookwithtest.Views

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.artbookwithtest.adapter.ArtRecyclerAdapter
import com.example.artbookwithtest.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter,
    private val glide: RequestManager)
    : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className) {
            FeedFragment::class.java.name -> FeedFragment(artRecyclerAdapter)
            DetailsArt::class.java.name -> DetailsArt(glide)
            ImageApi::class.java.name -> ImageApi(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }

}