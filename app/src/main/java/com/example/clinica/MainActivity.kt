package com.example.clinica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clinica.screens.*
import com.example.clinica.screens.doctor.DoctorHomeScreen
import com.example.clinica.screens.DoctorLoginScreen
import com.example.clinica.screens.DoctorRegisterScreen
import com.example.clinica.screens.patient.PatientHomeScreen
import com.example.clinica.ui.login.theme.DoctorAppTheme
import com.example.clinica.data.DoctorDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoctorAppTheme {
                Surface {
                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "role_selection") {

                        // Tela inicial — escolher se é paciente ou doutor
                        composable("role_selection") {
                            RoleSelectionScreen(
                                onDoctorSelected = { navController.navigate("doctor_login") },
                                onPatientSelected = { navController.navigate("patient_home") }
                            )
                        }

                        // Tela de login do doutor
                        composable("doctor_login") {
                            DoctorLoginScreen(
                                onLoginSuccess = {
                                    val doctor = DoctorDatabase.getFirstDoctor() // pegar algum doctor de exemplo
                                    navController.navigate("doctor_home/${doctor.id}")
                                },
                                onRegisterClick = { navController.navigate("doctor_register") }
                            )
                        }

                        // Tela de registro do doutor
                        composable("doctor_register") {
                            DoctorRegisterScreen(
                                onRegisterSuccess = {
                                    val doctor = DoctorDatabase.getLastDoctor() // pegar último cadastrado
                                    navController.navigate("doctor_home/${doctor.id}")
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                        // Tela inicial do doutor (passando id)
                        composable(
                            route = "doctor_home/{doctorId}",
                        ) { backStackEntry ->
                            val doctorId = backStackEntry.arguments?.getString("doctorId")?.toInt() ?: 0
                            val doctor = DoctorDatabase.getDoctorById(doctorId)
                            DoctorHomeScreen(doctor = doctor)
                        }


                    }
                }
            }
        }
    }
}
