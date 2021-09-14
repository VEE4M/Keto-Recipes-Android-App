package com.gmail.appverstas.ketorecipes.viewmodels

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.gmail.appverstas.ketorecipes.repositories.Repository
import com.gmail.appverstas.ketorecipes.data.local.entities.FavouritesEntity
import com.gmail.appverstas.ketorecipes.data.local.entities.RecipesEntity
import com.gmail.appverstas.ketorecipes.data.models.RecipesResponse
import com.gmail.appverstas.ketorecipes.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 *Veli-Matti Tikkanen, 26.8.2021
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _recipesResponse: MutableLiveData<NetworkResult<RecipesResponse>> = MutableLiveData()
    val recipesResponse: LiveData<NetworkResult<RecipesResponse>> = _recipesResponse
    var searchedRecipesResponse: MutableLiveData<NetworkResult<RecipesResponse>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQueries: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQueries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipesResponse.value = NetworkResult.Loading()
        if (isConnectedToInternet()) {
            try {
                val response = repository.remoteSource.getRecipes(queries)
                Log.d(TAG, "getRecipesSafeCall: $response")
                _recipesResponse.value = handleResponse(response)
                val recipes = _recipesResponse.value?.data
                recipes?.let { 
                    offlineCacheRecipes(it)
                }
            } catch (e: Exception) {
                _recipesResponse.value = NetworkResult.Error(message = "Recipes not found")
            }

        } else {
            _recipesResponse.value = NetworkResult.Error(message = "No internet connection")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQueries: Map<String, String>) {
        searchedRecipesResponse.value = NetworkResult.Loading()
        if (isConnectedToInternet()) {
            try {
                val response = repository.remoteSource.searchRecipes(searchQueries)
                Log.d(TAG, "getRecipesSafeCall: $response")
                searchedRecipesResponse.value = handleResponse(response)
            } catch (e: Exception) {
                searchedRecipesResponse.value = NetworkResult.Error(message = "Recipes not found")
            }

        } else {
            searchedRecipesResponse.value = NetworkResult.Error(message = "No internet connection")
        }
    }



    private fun handleResponse(response: Response<RecipesResponse>): NetworkResult<RecipesResponse> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error(message = "API Key limited.")
            }
            response.body()!!.list.isNullOrEmpty() -> {
                return NetworkResult.Error(message = "Recipes not found")
            }
            response.isSuccessful -> {
                val recipes = response.body()
                return NetworkResult.Success(recipes!!)
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


    private fun offlineCacheRecipes(recipes: RecipesResponse) {
        val recipesEntity = RecipesEntity(recipes)
        insertToDatabase(recipesEntity)
    }

    val allData: LiveData<List<RecipesEntity>> = repository.localSource.getRecipes().asLiveData()
    val favourites: LiveData<List<FavouritesEntity>> = repository.localSource.getFavourites().asLiveData()

    private fun insertToDatabase(recipesEntity: RecipesEntity) {
        viewModelScope.launch {
            repository.localSource.insertRecipes(recipesEntity)
        }
    }


    fun insertFavourite(favouritesEntity: FavouritesEntity) {
        viewModelScope.launch {
            repository.localSource.insertFavourite(favouritesEntity)
        }
    }

    fun deleteFavourite(favouritesEntity: FavouritesEntity) {
        viewModelScope.launch {
            repository.localSource.deleteFavourite(favouritesEntity)
        }
    }

}