package com.gmail.appverstas.ketorecipes.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.appverstas.ketorecipes.data.local.DataStoreRepository
import com.gmail.appverstas.ketorecipes.util.Constants.API_KEY
import com.gmail.appverstas.ketorecipes.util.Constants.DEFAULT_DIET_TYPE
import com.gmail.appverstas.ketorecipes.util.Constants.DEFAULT_MEAL_TYPE
import com.gmail.appverstas.ketorecipes.util.Constants.DEFAULT_NUMBER_OF_RESULTS
import com.gmail.appverstas.ketorecipes.util.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.gmail.appverstas.ketorecipes.util.Constants.QUERY_API_KEY
import com.gmail.appverstas.ketorecipes.util.Constants.QUERY_DIET
import com.gmail.appverstas.ketorecipes.util.Constants.QUERY_FILL_INGREDIENTS
import com.gmail.appverstas.ketorecipes.util.Constants.QUERY_NUMBER
import com.gmail.appverstas.ketorecipes.util.Constants.QUERY_SEARCH
import com.gmail.appverstas.ketorecipes.util.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Veli-Matti Tikkanen, 27.8.2021
 */

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    application: Application
) :
    AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE

    var networkStatus = false
    val readMealType = dataStoreRepository.readMealType

    fun saveMealType(mealType: String, mealTypeId: Int){
        viewModelScope.launch {
            dataStoreRepository.saveMealType(mealType, mealTypeId)
        }
    }

    fun applyQueries(searchQuery: String = ""): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        viewModelScope.launch {
            readMealType.collect { value ->
                mealType = value.selectedMealType
            }
        }
        if(searchQuery.isNotEmpty()){
            queries[QUERY_SEARCH] = searchQuery
        }
        queries[QUERY_NUMBER] = DEFAULT_NUMBER_OF_RESULTS
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

}