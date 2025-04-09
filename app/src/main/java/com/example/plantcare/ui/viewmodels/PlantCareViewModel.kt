package com.example.plantcare.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.plantcare.Authentication.FirebaseAuthRepository

class PlantCareViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {
    fun signOut() {
        firebaseAuthRepository.signOut()
    }
}