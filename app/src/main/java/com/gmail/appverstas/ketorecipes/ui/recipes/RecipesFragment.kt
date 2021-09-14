package com.gmail.appverstas.ketorecipes.ui.recipes

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.appverstas.ketorecipes.viewmodels.MainViewModel
import com.gmail.appverstas.ketorecipes.R
import com.gmail.appverstas.ketorecipes.adapters.RecipesAdapter
import com.gmail.appverstas.ketorecipes.databinding.FragmentRecipesBinding
import com.gmail.appverstas.ketorecipes.util.NetworkListener
import com.gmail.appverstas.ketorecipes.util.NetworkResult
import com.gmail.appverstas.ketorecipes.viewmodels.RecipesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {


    private val args: RecipesFragmentArgs? by navArgs()
    private lateinit var binding: FragmentRecipesBinding
    private lateinit var networkListener: NetworkListener
    private var backFromBottomSheet: Boolean? = false
    private var adapter = RecipesAdapter()
    private val mainViewModel: MainViewModel by viewModels()
    private val recipesViewModel: RecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecipesBinding.bind(view)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        backFromBottomSheet = args?.backFromBottomSheetDialog

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setHasOptionsMenu(true)

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    recipesViewModel.networkStatus = status
                    showShimmerEffect()
                    observeDatabase()
                }
        }

        adapter.setOnItemClickListener {
            findNavController().navigate(
                RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(
                    it
                )
            )
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheetFragment)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { searchApiData(query) }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applyQueries(searchQuery))
        mainViewModel.searchedRecipesResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val recipes = response.data
                    recipes?.let { recipes ->
                        adapter.updateRecipesList(recipes.list)
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Snackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_SHORT)
                        .setAction(getString(R.string.snackbar_action_ok)) {}
                        .show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun showShimmerEffect() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerView.hideShimmer()
    }

    private fun observeDatabase() {
        lifecycleScope.launch {
            mainViewModel.allData.observe(viewLifecycleOwner, Observer { listOfEntities ->
                if (listOfEntities.isNotEmpty() && !backFromBottomSheet!!) {
                    adapter.updateRecipesList(listOfEntities[0].recipesResponse.list)
                    hideShimmerEffect()
                } else {
                    getDataFromApi()
                    backFromBottomSheet = false
                    hideShimmerEffect()
                }
            })
        }
    }

    private fun getDataFromApi() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    response.message?.let {
                        if (!it.contains("internet")) {
                            Snackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_SHORT)
                                .setAction(getString(R.string.snackbar_action_ok)) {}
                                .show()
                        }
                    }

                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

}