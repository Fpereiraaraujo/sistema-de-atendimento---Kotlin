package com.example.clinica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clinica.ui.login.WelcomeScreen
import com.example.clinica.ui.login.AvailabilityScreen
import com.example.clinica.ui.login.DoctorLoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "welcome") {
                        composable("welcome") {
                            WelcomeScreen(
                                onDoctorClick = { navController.navigate("doctorLogin") },
                                onPatientClick = { /* Navigate to patient flow later */ }
                            )
                        }
                        composable("doctorLogin") {
                            DoctorLoginScreen(
                                onLoginSuccess = { navController.navigate("availability") }
                            )
                        }
                        composable("availability") {
                            AvailabilityScreen()
                        }
                    }
                }
            }
        }
    }
}
