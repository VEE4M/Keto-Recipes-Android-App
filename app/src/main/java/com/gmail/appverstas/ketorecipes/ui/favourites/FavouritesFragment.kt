package com.gmail.appverstas.ketorecipes.ui.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.appverstas.ketorecipes.R
import com.gmail.appverstas.ketorecipes.adapters.FavouritesAdapter
import com.gmail.appverstas.ketorecipes.databinding.FragmentFavouritesBinding
import com.gmail.appverstas.ketorecipes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FavouritesAdapter()
        binding = FragmentFavouritesBinding.bind(view)
        binding.mainViewModel = mainViewModel
        binding.adapter = adapter
        binding.favouritesRecyclerview.adapter = adapter
        binding.favouritesRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        mainViewModel.favourites.observe(viewLifecycleOwner, Observer { updatedList ->
            if(updatedList.isNullOrEmpty()){
                binding.favouritesRecyclerview.visibility = View.INVISIBLE
                binding.ivNoFavourites.visibility = View.VISIBLE
                binding.tvNoFavourites.visibility = View.VISIBLE
            }
            adapter.updateFavouritesList(updatedList)
            binding.favouritesRecyclerview.adapter = adapter

        })

        adapter.setOnItemClickListener {
            findNavController().navigate(FavouritesFragmentDirections.actionFavouritesFragmentToDetailsActivity(it))
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}