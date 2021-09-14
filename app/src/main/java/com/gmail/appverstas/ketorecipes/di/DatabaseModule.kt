package com.gmail.appverstas.ketorecipes.di

import android.content.Context
import androidx.room.Room
import com.gmail.appverstas.ketorecipes.data.local.KetoRecipesDao
import com.gmail.appverstas.ketorecipes.data.local.KetoRecipesDatabase
import com.gmail.appverstas.ketorecipes.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *Veli-Matti Tikkanen, 30.8.2021
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): KetoRecipesDatabase =
        Room.databaseBuilder(context, KetoRecipesDatabase::class.java, Constants.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideKetoRecipesDao(db: KetoRecipesDatabase): KetoRecipesDao = db.ketoRecipesDao()
}