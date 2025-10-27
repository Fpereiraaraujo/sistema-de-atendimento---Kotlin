package com.example.clinica.model

data class Doctor(
    val id: Int,
    val name: String,
    val specialty: String,
    val crm: String,
    val email: String,
    val phone: String,
    val description: String
)
