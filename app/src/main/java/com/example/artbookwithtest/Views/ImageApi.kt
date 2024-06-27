package com.example.artbookwithtest.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.artbookwithtest.adapter.ImageRecyclerAdapter
import com.example.artbookwithtest.databinding.FragmentImageApiBinding
import com.example.artbookwithtest.util.Status
import com.example.artbookwithtest.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        subscibeToObservers()
        binding.imagesRecyclerView.adapter = imageRecyclerAdapter
        binding.imagesRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)

        var job : Job? = null

        binding.searchImage.addTextChangedListener { editable->
            job?.cancel()

            job = lifecycleScope.launch {
                delay(1000)
                editable?.let {
                    if (editable.isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }


        imageRecyclerAdapter.setOnItemClickerListener { it->
            viewModel.setSelectedImage(it)
            findNavController().popBackStack()

        }


    }

    fun subscibeToObservers(){
        viewModel.imageList.observe(viewLifecycleOwner,{ it ->
            when(it.status){ //internetten çektiğimiz verinin tamamı
                Status.SUCCESS ->{
                    val url = it.data?.let {results  ->
                        results.hits?.let { hits->
                            hits.map {imageResult ->
                                imageResult.previewURL
                            } } }
                    imageRecyclerAdapter.images = url ?: listOf()
                    binding.progressBar.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),"There is an error",Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }

                Status.LOADING -> {

                    binding.progressBar.visibility = View.VISIBLE
                }

            }
        })
    }










    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}