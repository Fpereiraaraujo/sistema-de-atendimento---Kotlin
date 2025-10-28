package com.example.clinica.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoleSelectionScreen(
    onDoctorSelected: () -> Unit,
    onPatientSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onDoctorSelected, modifier = Modifier.fillMaxWidth()) {
            Text("I am a Doctor")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onPatientSelected, modifier = Modifier.fillMaxWidth()) {
            Text("I am a Patient")
        }
    }
}
