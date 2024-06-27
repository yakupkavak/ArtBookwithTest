package com.example.artbookwithtest.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artbookwithtest.adapter.ArtRecyclerAdapter
import com.example.artbookwithtest.databinding.FragmentFeedBinding
import com.example.artbookwithtest.viewmodel.ArtViewModel
import javax.inject.Inject

class FeedFragment @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter
) : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel : ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback
        (0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val selectedPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[selectedPosition]
            viewModel.deleteArt(selectedArt)

     }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        subscireToObserves()
        binding.feedRecycler.adapter = artRecyclerAdapter
        binding.feedRecycler.layoutManager = LinearLayoutManager(requireContext()  )
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.feedRecycler)

        binding.fab.setOnClickListener {
            findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToDetailsArt())
        }
    }


        //BURADA VALUE OLARAK SET ETTIĞIMIZ VERİYİ GÜNCELLİYORUZ
    private fun subscireToObserves(){
        viewModel.artList.observe(viewLifecycleOwner,{
            artRecyclerAdapter.arts = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}