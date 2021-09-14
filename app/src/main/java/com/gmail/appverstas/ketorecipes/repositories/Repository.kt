package com.gmail.appverstas.ketorecipes.repositories

import com.gmail.appverstas.ketorecipes.data.local.LocalDataSource
import com.gmail.appverstas.ketorecipes.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 *Veli-Matti Tikkanen, 26.8.2021
 */

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remoteSource = remoteDataSource
    val localSource = localDataSource
}