package com.example.clinica.screens.doctor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clinica.components.AppointmentCard
import com.example.clinica.data.FakeDatabase

@Composable
fun ScheduleScreen(doctorId: Int) {

    val appointments = FakeDatabase.appointments.filter { it.doctorId == doctorId.toString() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(appointments) { appointment ->
            AppointmentCard(appointment = appointment)
        }
    }
}
