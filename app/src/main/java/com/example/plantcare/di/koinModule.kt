package com.example.plantcare.di

import androidx.room.Room
import com.example.plantcare.ui.viewmodels.PlantListViewModel
import com.example.plantcare.ui.viewmodels.PlantFormViewModel
import com.example.plantcare.Repository.PlantRepository
import com.example.plantcare.database.PlantDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::PlantListViewModel)
    viewModelOf(::PlantFormViewModel)
}

val storageModule = module {
    singleOf(::PlantRepository)

    single {
        Room.databaseBuilder(
            androidContext(),
            PlantDataBase::class.java,
            "plant-database").build()
    }

    single {
        get<PlantDataBase>().plantDao()
    }
}