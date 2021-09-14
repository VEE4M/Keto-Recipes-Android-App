package com.gmail.appverstas.ketorecipes.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.gmail.appverstas.ketorecipes.R
import com.gmail.appverstas.ketorecipes.data.models.Recipe
import com.gmail.appverstas.ketorecipes.databinding.FragmentOverviewBinding
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {


    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOverviewBinding.bind(view)

        val args = arguments
        val myBundle: Recipe? = args?.getParcelable("recipeBundle")

        myBundle?.let {
            binding.tvTitle.text = it.title
            binding.ivFoodImage.load(it.image)
            binding.tvSummary.text = Jsoup.parse(it.summary).text()
            binding.tvTime.text = it.readyInMinutes.toString()
            binding.tvFavouritedCount.text = it.aggregateLikes.toString()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}