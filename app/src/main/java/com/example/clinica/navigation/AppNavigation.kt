package com.example.clinica.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clinica.data.FakeDatabase
import com.example.clinica.screens.doctor.DoctorHomeScreen
import com.example.clinica.screens.doctor.ScheduleScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val doctor = FakeDatabase.doctors.first()

    NavHost(navController = navController, startDestination = "doctor_home") {
        composable("doctor_home") {
            DoctorHomeScreen(doctor, navController)
        }
        composable("schedule") {
            ScheduleScreen(doctor.id)
        }
    }
}