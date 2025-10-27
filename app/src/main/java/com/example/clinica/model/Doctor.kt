package com.example.clinica.model

data class Doctor(
    val id: Int,
    val name: String,
    val specialty: String,
    val rating: Double,
    val availableSlots: List<String>
)
