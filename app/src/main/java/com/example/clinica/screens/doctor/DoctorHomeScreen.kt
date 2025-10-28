package com.example.clinica.screens.doctor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clinica.model.Doctor
import com.example.clinica.model.TimeSlot

@Composable
fun DoctorHomeScreen(doctor: Doctor) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Welcome, ${doctor.name}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Your Availability:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(doctor.availability) { slot: TimeSlot ->
                Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(slot.day)
                        Text("${slot.startHour} - ${slot.endHour}")
                    }
                }
            }
        }
    }
}
