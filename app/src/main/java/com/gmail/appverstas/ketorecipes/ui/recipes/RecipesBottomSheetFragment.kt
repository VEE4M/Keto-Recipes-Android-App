package com.gmail.appverstas.ketorecipes.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.gmail.appverstas.ketorecipes.R
import com.gmail.appverstas.ketorecipes.util.Constants.DEFAULT_MEAL_TYPE
import com.gmail.appverstas.ketorecipes.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes_bottom_sheet.view.*

@AndroidEntryPoint
class RecipesBottomSheetFragment : BottomSheetDialogFragment() {

    private val recipesViewModel: RecipesViewModel by viewModels()
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesViewModel.readMealType.asLiveData().observe(viewLifecycleOwner, Observer { values ->
            mealTypeChip = values.selectedMealType
            updateChip(values.selectedMealTypeId, view.chipGroup_mealType)
        })

        view.chipGroup_mealType.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val mealType = chip.text.toString().lowercase()
            mealTypeChip = mealType
            mealTypeChipId = checkedId
        }

        view.btn_apply.setOnClickListener {
            recipesViewModel.saveMealType(mealTypeChip, mealTypeChipId)
            findNavController().navigate(RecipesBottomSheetFragmentDirections.actionRecipesBottomSheetFragmentToRecipesFragment(true))
        }
    }


    private fun updateChip(selectedMealTypeId: Int, chipGroup: ChipGroup) {
        if (selectedMealTypeId != 0) {
            val chip = chipGroup.findViewById<Chip>(selectedMealTypeId)
            chip.isChecked = true
        }
    }

}


