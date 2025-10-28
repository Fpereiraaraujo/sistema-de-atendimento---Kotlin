package com.example.clinica.model

data class Doctor(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val specialty: String,
    val crm: String,
    val phone: String,
    val description: String,
    val availability: MutableList<TimeSlot> = mutableListOf()
)


data class TimeSlot(
    val day: String,
    val startHour: String,
    val endHour: String
)
