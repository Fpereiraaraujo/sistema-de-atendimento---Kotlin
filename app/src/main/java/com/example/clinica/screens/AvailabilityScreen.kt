package com.example.clinica.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.clinica.model.Doctor
import com.example.clinica.model.TimeSlot

@Composable
fun AvailabilityScreen(doctor: Doctor) {
    var day by remember { mutableStateOf("") }
    var startHour by remember { mutableStateOf("") }
    var endHour by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Add New Time Slot", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = day, onValueChange = { day = it }, label = { Text("Day") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = startHour,
            onValueChange = { startHour = it },
            label = { Text("Start Hour (HH:mm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = endHour,
            onValueChange = { endHour = it },
            label = { Text("End Hour (HH:mm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (day.isNotEmpty() && startHour.isNotEmpty() && endHour.isNotEmpty()) {
                doctor.availability.add(TimeSlot(day, startHour, endHour))
                day = ""; startHour = ""; endHour = ""
            }
        }) {
            Text("Add Slot")
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Current Slots:", style = MaterialTheme.typography.titleMedium)
        doctor.availability.forEach {
            Text("${it.day}: ${it.startHour} - ${it.endHour}")
        }
    }
}
