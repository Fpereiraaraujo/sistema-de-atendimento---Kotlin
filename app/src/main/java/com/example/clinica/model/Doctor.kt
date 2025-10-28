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


@Entity
data class TimeSlotEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val doctorId: Int,
    val day: String,
    val startHour: String,
    val endHour: String
)