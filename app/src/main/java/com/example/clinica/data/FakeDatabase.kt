package com.example.clinica.data

import com.example.clinica.model.Appointment
import com.example.clinica.model.Doctor

object FakeDatabase {
    val doctors = mutableListOf(
        Doctor(1, "Dr. John Smith", "Cardiologist", "CRM1234", "john@clinic.com", "99999-9999", "Experienced in heart diseases."),
        Doctor(2, "Dr. Ana Perez", "Dermatologist", "CRM5678", "ana@clinic.com", "98888-8888", "Skin and aesthetic treatments.")
    )

    val appointments = mutableListOf(
        Appointment(1, 1, "Alice Brown", "2025-10-30", "14:00", "Scheduled"),
        Appointment(2, 1, "Robert White", "2025-10-31", "09:00", "Completed")
    )
}