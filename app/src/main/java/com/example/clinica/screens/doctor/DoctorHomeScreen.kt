package com.example.clinica.screens.doctor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.clinica.model.Doctor

@Composable
fun DoctorHomeScreen(navController: NavController, doctor: Doctor) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Welcome, Dr. ${doctor.name}", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("schedule") }) {
            Text("View Schedule")
        }
    }
}
