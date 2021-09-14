package com.gmail.appverstas.ketorecipes.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.appverstas.ketorecipes.R
import com.gmail.appverstas.ketorecipes.adapters.IngredientsAdapter
import com.gmail.appverstas.ketorecipes.data.models.Recipe
import kotlinx.android.synthetic.main.fragment_ingredients.view.*


class IngredientsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = IngredientsAdapter()
        view.ingredientsRecyclerView.adapter = adapter
        view.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val args = arguments
        val myBundle: Recipe? = args?.getParcelable("recipeBundle")

        myBundle?.let {
            adapter.setListOfIngredients(it.extendedIngredients)
        }


    }
}