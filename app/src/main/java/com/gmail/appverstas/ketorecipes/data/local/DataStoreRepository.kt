package com.gmail.appverstas.ketorecipes.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gmail.appverstas.ketorecipes.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 *Veli-Matti Tikkanen, 31.8.2021
 */
private val Context.myDataStore by preferencesDataStore(Constants.PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(Constants.PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(Constants.PREFERENCES_MEAL_TYPE_ID)
    }

    suspend fun saveMealType(mealType: String, mealTypeId: Int) {
        context.myDataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
        }
    }

    val readMealType: Flow<MealType> = context.myDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferencecs ->
            val selectedMealType = preferencecs[PreferenceKeys.selectedMealType] ?: Constants.DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferencecs[PreferenceKeys.selectedMealTypeId] ?: 0
            MealType(selectedMealType, selectedMealTypeId)
        }

}


data class MealType(
    val selectedMealType: String,
    val selectedMealTypeId: Int
)