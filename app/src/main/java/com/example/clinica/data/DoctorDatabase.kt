package com.example.clinica.data

import com.example.clinica.model.Doctor

object DoctorDatabase {
    private val doctors = mutableListOf<Doctor>()
    private var idCounter = 1

    fun addDoctor(doctor: Doctor) {
        doctors.add(doctor)
    }

    fun getDoctors(): List<Doctor> = doctors

    fun nextId(): Int {
        return idCounter++
    }

    fun findDoctorByEmailAndPassword(email: String, password: String): Doctor? {
        return doctors.find { it.email == email && it.password == password }
    }

    // NOVAS FUNÇÕES NECESSÁRIAS PARA NAVEGAÇÃO
    fun getDoctorById(id: Int): Doctor {
        return doctors.first { it.id == id }
    }

    fun getFirstDoctor(): Doctor {
        return doctors.first()
    }

    fun getLastDoctor(): Doctor {
        return doctors.last()
    }
}
