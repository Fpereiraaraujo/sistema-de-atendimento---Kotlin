package com.example.clinica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clinica.data.AppDatabase
import com.example.clinica.model.Doctor
import com.example.clinica.screens.AvailabilityScreen
import com.example.clinica.screens.DoctorLoginScreen
import com.example.clinica.screens.DoctorRegisterScreen
import com.example.clinica.screens.doctor.DoctorHomeScreen
import com.example.clinica.screens.patient.BookAppointmentScreen
import com.example.clinica.screens.patient.PatientHomeScreen
import com.example.clinica.screens.patient.PatientLoginScreen
import com.example.clinica.screens.patient.PatientRegisterScreen
import com.example.clinica.ui.login.WelcomeScreen
import com.example.clinica.ui.login.theme.DoctorAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoctorAppTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "role_selection"
                    ) {
                        // ===== Seleção de papel =====
                        composable("role_selection") {
                            WelcomeScreen(
                                onDoctorClick = { navController.navigate("doctor_login") },
                                onPatientClick = { navController.navigate("patient_login") }
                            )
                        }

                        // ===== Fluxo MÉDICO =====
                        composable("doctor_login") {
                            DoctorLoginScreen(
                                onLoginSuccess = { doctor ->
                                    navController.navigate("doctor_home/${doctor.id}")
                                },
                                onRegisterClick = { navController.navigate("doctor_register") }
                            )
                        }

                        composable("doctor_register") {
                            DoctorRegisterScreen(
                                onRegisterSuccess = { doctor ->
                                    navController.navigate("doctor_home/${doctor.id}")
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                        composable("doctor_home/{doctorId}") { backStackEntry ->
                            val doctorId = backStackEntry.arguments?.getString("doctorId")?.toInt() ?: 0
                            val context = LocalContext.current
                            var doctor by remember { mutableStateOf<Doctor?>(null) }

                            LaunchedEffect(doctorId) {
                                val db = AppDatabase.getDatabase(context)
                                doctor = withContext(Dispatchers.IO) {
                                    db.doctorDao().getDoctorById(doctorId)
                                }
                            }

                            doctor?.let {
                                DoctorHomeScreen(doctor = it, navController = navController)
                            } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }

                        composable("doctor_availability/{doctorId}") { backStackEntry ->
                            val doctorId = backStackEntry.arguments?.getString("doctorId")?.toIntOrNull()
                            val context = LocalContext.current
                            var doctor by remember { mutableStateOf<Doctor?>(null) }

                            if (doctorId != null) {
                                LaunchedEffect(doctorId) {
                                    val db = AppDatabase.getDatabase(context)
                                    doctor = withContext(Dispatchers.IO) {
                                        db.doctorDao().getDoctorById(doctorId)
                                    }
                                }
                            }

                            doctor?.let {
                                AvailabilityScreen(doctor = it, navController = navController)
                            } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }

                        // ===== Fluxo PACIENTE =====
                        composable("patient_login") {
                            PatientLoginScreen(
                                onLogged = { patientId ->
                                    navController.navigate("patient_home/$patientId") {
                                        popUpTo("role_selection") { inclusive = false }
                                        launchSingleTop = true
                                    }
                                },
                                goRegister = { navController.navigate("patient_register") },
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                        composable("patient_register") {
                            PatientRegisterScreen(
                                onRegistered = { patientId ->
                                    navController.navigate("patient_home/$patientId") {
                                        popUpTo("role_selection") { inclusive = false }
                                        launchSingleTop = true
                                    }
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        // Dashboard do paciente
                        composable("patient_home/{patientId}") { backStackEntry ->
                            val patientId = backStackEntry.arguments?.getString("patientId") ?: return@composable
                            PatientHomeScreen(
                                patientId = patientId,
                                onLogout = {
                                    navController.navigate("role_selection") {
                                        popUpTo("role_selection") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                                onSchedule = {   // << nome correto do callback
                                    navController.navigate("book_appointment/$patientId")
                                }
                            )
                        }

                        // Agendar consulta/exame
                        composable("book_appointment/{patientId}") { backStackEntry ->
                            val patientId = backStackEntry.arguments?.getString("patientId") ?: return@composable
                            BookAppointmentScreen(
                                patientId = patientId,
                                patientName = "Paciente",
                                onBooked = {
                                    // Após confirmar, volta ao dashboard do paciente
                                    navController.navigate("patient_home/$patientId") {
                                        popUpTo("patient_home/$patientId") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
