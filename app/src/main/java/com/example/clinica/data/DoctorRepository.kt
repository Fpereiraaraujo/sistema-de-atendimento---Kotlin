// DoctorRepository.kt
package com.example.clinica.data

import android.content.Context
import androidx.room.Room
import com.example.clinica.model.Doctor

class DoctorRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "clinica-db"
    ).build()

    private val dao = db.doctorDao()

    suspend fun insertDoctor(doctor: Doctor) = dao.insert(doctor)
    suspend fun getDoctor(email: String, password: String) = dao.findByEmailAndPassword(email, password)
    suspend fun getAllDoctors() = dao.getAll()
}
