package com.example.clinica.model

data class Appointment(
    val doctorId: String,
    val patientId: String,
    val patientName: String,
    val date: String,
    val time: String,
    val status: String
)