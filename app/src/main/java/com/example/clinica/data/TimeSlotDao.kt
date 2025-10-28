package com.example.clinica.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.clinica.model.TimeSlotEntity

@Dao
interface TimeSlotDao {
    @Insert
    suspend fun insert(timeSlot: TimeSlotEntity)

    @Query("SELECT * FROM TimeSlotEntity WHERE doctorId = :doctorId")
    suspend fun getTimeSlotsByDoctor(doctorId: Int): List<TimeSlotEntity>
}