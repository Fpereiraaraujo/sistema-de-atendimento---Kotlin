package com.example.clinica.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctors")
data class Doctor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val specialty: String,
    val crm: String,
    val phone: String,
    val description: String

)

// TimeSlot continua igual
data class TimeSlot(
    val day: String,
    val startHour: String,
    val endHour: String
)