package com.example.plantcare.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.plantcare.Authentication.FirebaseAuthRepository
import com.example.plantcare.Repository.PlantRepository

class PlantCareViewModel(
    private val repository: PlantRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {
    fun signOut() {
        firebaseAuthRepository.signOut()
    }
}