package com.example.artbookwithtest.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.artbookwithtest.databinding.FragmentImageApiBinding

class ImageApi : Fragment() {

    private var _binding: FragmentImageApiBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentImageApiBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}