package com.example.clinica.data

import com.example.clinica.model.Patient
import java.util.UUID

object PatientRepository {
    private val pacientes = mutableListOf<Patient>()

    fun register(name: String, email: String, password: String): Patient {
        if (pacientes.any { it.email.equals(email, ignoreCase = true) }) {
            throw IllegalStateException("Email já cadastrado")
        }
        val p = Patient(
            id = UUID.randomUUID().toString(),
            name = name,
            email = email,
            password = password
        )
        pacientes.add(p)
        return p
    }

    fun login(email: String, password: String): Patient? =
        pacientes.firstOrNull { it.email.equals(email, ignoreCase = true) && it.password == password }
}
