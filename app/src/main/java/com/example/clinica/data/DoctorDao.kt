package com.example.clinica.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.clinica.model.Doctor

@Dao
interface DoctorDao {
    @Insert
    suspend fun insert(doctor: Doctor): Long   // Retorna o ID gerado

    @Query("SELECT * FROM doctors WHERE email = :email AND password = :password LIMIT 1")
    suspend fun findByEmailAndPassword(email: String, password: String): Doctor?

    @Query("SELECT * FROM doctors WHERE id = :id")
    suspend fun getDoctorById(id: Int): Doctor?

    @Query("SELECT * FROM doctors")
    suspend fun getAll(): List<Doctor>
}
