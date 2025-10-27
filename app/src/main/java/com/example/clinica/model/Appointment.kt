package com.example.clinica.model

data class Appointment(
    val id: Int,
    val doctorId: Int,
    val patientName: String,
    val date: String,
    val time: String,
    val status: String // "Scheduled", "Completed", "Canceled"
)
