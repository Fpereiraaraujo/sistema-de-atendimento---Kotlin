package com.example.clinica.data

import com.example.clinica.model.Doctor
import com.example.clinica.model.TimeSlot

val sampleDoctor = Doctor(
    id = 1,
    name = "Dr. John Doe",
    email = "doctor@test.com",
    password = "123456",           // <-- adicione isso
    crm = "123456",
    phone = "999999999",
    description = "Experienced cardiologist",
    specialty = "Cardiologist",
    availability = mutableListOf(
        TimeSlot("Monday", "09:00", "12:00"),
        TimeSlot("Wednesday", "14:00", "18:00")
    )
)
