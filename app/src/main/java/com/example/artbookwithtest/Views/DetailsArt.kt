package com.example.artbookwithtest.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.artbookwithtest.Views.DetailsArtDirections
import com.example.artbookwithtest.databinding.FragmentDetailsArtBinding
import com.example.artbookwithtest.util.Status
import com.example.artbookwithtest.viewmodel.ArtViewModel
import javax.inject.Inject

class DetailsArt @Inject constructor(private val glide: RequestManager) : Fragment() {

    private var _binding: FragmentDetailsArtBinding? = null

    private val binding get() = _binding!!
    lateinit var viewModel: ArtViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsArtBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        binding.selectImage.setOnClickListener {
            findNavController().navigate(DetailsArtDirections.actionDetailsArtToImageApi())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        subscireToObserves()

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(binding.editName.text.toString(),
                binding.editArtist.text.toString(),
                binding.editYear.text.toString())
        }

    }

    private fun subscireToObserves(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner,{url ->
            glide.load(url).into(binding.selectImage)
        })
        viewModel.insertArtMessage.observe(viewLifecycleOwner,{

            when(it.status){
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()

                }

                Status.LOADING -> {

                }
            }



        })



    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}