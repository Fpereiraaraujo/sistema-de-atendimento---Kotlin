package com.example.clinica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clinica.data.AppDatabase
import com.example.clinica.model.Doctor
import com.example.clinica.screens.*
import com.example.clinica.screens.doctor.DoctorHomeScreen
import com.example.clinica.screens.patient.PatientHomeScreen
import com.example.clinica.ui.login.WelcomeScreen
import com.example.clinica.ui.login.theme.DoctorAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoctorAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "role_selection") {

                        // Tela de seleção de papel (doutor ou paciente)
                        composable("role_selection") {
                            WelcomeScreen(
                                onDoctorClick = { navController.navigate("doctor_login") },
                                onPatientClick = { navController.navigate("patient_home") }
                            )
                        }

                        // Login de doutor
                        composable("doctor_login") {
                            DoctorLoginScreen(
                                onLoginSuccess = { doctor ->
                                    navController.navigate("doctor_home/${doctor.id}")
                                },
                                onRegisterClick = { navController.navigate("doctor_register") }
                            )
                        }

                        // Registro de doutor
                        composable("doctor_register") {
                            DoctorRegisterScreen(
                                onRegisterSuccess = { doctor ->
                                    navController.navigate("doctor_home/${doctor.id}")
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                        // Home do doutor
                        composable("doctor_home/{doctorId}") { backStackEntry ->
                            val doctorId =
                                backStackEntry.arguments?.getString("doctorId")?.toInt() ?: 0
                            val context = LocalContext.current
                            var doctor by remember { mutableStateOf<Doctor?>(null) }

                            LaunchedEffect(doctorId) {
                                val db = AppDatabase.getDatabase(context)
                                doctor = withContext(Dispatchers.IO) {
                                    db.doctorDao().getDoctorById(doctorId)
                                }
                            }

                            doctor?.let {
                                DoctorHomeScreen(
                                    doctor = it,
                                    navController = navController
                                )
                            } ?: run {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        // Tela de disponibilidade do doutor
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
                                AvailabilityScreen(
                                    doctor = it,
                                    navController = navController
                                )
                            } ?: run {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Erro: Doutor não encontrado")
                                    // ou CircularProgressIndicator() se estiver carregando
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
