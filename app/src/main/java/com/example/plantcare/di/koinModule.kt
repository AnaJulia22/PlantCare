package com.example.plantcare.di

import androidx.room.Room
import com.example.plantcare.ui.viewmodels.PlantListViewModel
import com.example.plantcare.ui.viewmodels.PlantFormViewModel
import com.example.plantcare.ui.viewmodels.SignUpViewModel
import com.example.plantcare.ui.viewmodels.SignInViewModel
import com.example.plantcare.ui.viewmodels.PlantCareViewModel
import com.example.plantcare.ui.viewmodels.PlantIdentifierViewModel
import com.example.plantcare.ui.viewmodels.PlantResultViewModel
import com.example.plantcare.Repository.PlantRepository
import com.example.plantcare.Repository.UserRepository
import com.example.plantcare.Authentication.FirebaseAuthRepository
import com.example.plantcare.database.PlantDataBase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::PlantListViewModel)
    viewModelOf(::PlantFormViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::PlantCareViewModel)
    viewModelOf(::PlantIdentifierViewModel)
    viewModelOf(::PlantResultViewModel)
}

val storageModule = module {
    singleOf(::PlantRepository)
    singleOf(::UserRepository)
    singleOf(::FirebaseAuthRepository)

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

val firebaseModule = module {
    single {
        Firebase.auth
    }
}