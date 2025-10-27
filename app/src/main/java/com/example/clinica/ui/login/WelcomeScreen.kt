package com.example.clinica.ui.login


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeScreen(
    onDoctorClick: () -> Unit,
    onPatientClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Who are you?")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onDoctorClick) {
                Text(text = "Doctor")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onPatientClick) {
                Text(text = "Patient")
            }
        }
    }
}
