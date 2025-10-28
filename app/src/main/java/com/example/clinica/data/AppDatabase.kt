package com.example.clinica.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.clinica.model.Doctor
import com.example.clinica.model.TimeSlotEntity

@Database(entities = [Doctor::class, TimeSlotEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun doctorDao(): DoctorDao
    abstract fun timeSlotDao(): TimeSlotDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "clinica_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}
