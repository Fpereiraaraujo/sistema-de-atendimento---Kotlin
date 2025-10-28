package com.example.clinica.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.clinica.model.Doctor

@Composable
fun DoctorHomeScreen(doctor: Doctor, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Olá, ${doctor.name} 👋",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Especialidade: ${doctor.specialty}", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text("CRM: ${doctor.crm}", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                Text("Telefone: ${doctor.phone}", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                Text("Descrição: ${doctor.description}", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("doctor_availability/${doctor.id}") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Ver Disponibilidade", style = MaterialTheme.typography.titleMedium)
        }
    }
}
