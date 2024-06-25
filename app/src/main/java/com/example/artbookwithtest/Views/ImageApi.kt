package com.example.artbookwithtest.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.artbookwithtest.adapter.ImageRecyclerAdapter
import com.example.artbookwithtest.databinding.FragmentImageApiBinding
import com.example.artbookwithtest.util.Status
import com.example.artbookwithtest.viewmodel.ArtViewModel
import javax.inject.Inject

class ImageApi @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment() {

    private var _binding: FragmentImageApiBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel : ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentImageApiBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        binding.imagesRecyclerView.adapter = imageRecyclerAdapter
        binding.imagesRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)

        imageRecyclerAdapter.setOnItemClickerListener { it->
            viewModel.setSelectedImage(it)
            findNavController().popBackStack()

        }


    }

    fun subscibeToObservers(){
        viewModel.imageList.observe(viewLifecycleOwner,{
            when(it.status){
                Status.SUCCESS -> TODO()
                Status.ERROR -> TODO()
                Status.LOADING -> TODO()
            }
        })
    }










    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}