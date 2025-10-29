package com.example.clinica.screens.doctor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.clinica.data.AppointmentRepository
import com.example.clinica.model.Appointment
import com.example.clinica.model.Doctor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorHomeScreen(doctor: Doctor, navController: NavController) {
    val scope = rememberCoroutineScope()
    val appointments = remember { mutableStateListOf<Appointment>() }

    // 🔹 Carrega as consultas marcadas para este médico
    LaunchedEffect(doctor.id) {
        val list = AppointmentRepository.byDoctor(doctor.id)
        appointments.clear()
        appointments.addAll(list)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Painel do Dr. ${doctor.name}") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // 📋 Informações do médico
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Especialidade: ${doctor.specialty}", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("CRM: ${doctor.crm}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Telefone: ${doctor.phone}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Descrição: ${doctor.description}", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(Modifier.height(20.dp))

            // 🕒 Botão para definir disponibilidade
            Button(
                onClick = { navController.navigate("doctor_availability/${doctor.id}") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Gerenciar Disponibilidade")
            }

            Spacer(Modifier.height(24.dp))

            // 📅 Lista de consultas marcadas
            Text("Consultas Agendadas", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))

            if (appointments.isEmpty()) {
                Text("Nenhuma consulta marcada.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(appointments) { appt ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text("Data: ${appt.date}", style = MaterialTheme.typography.titleMedium)
                                Text("Hora: ${appt.time}")
                                Text("ID do Paciente: ${appt.patientId}")

                                Spacer(Modifier.height(8.dp))

                                OutlinedButton(
                                    onClick = {
                                        scope.launch(Dispatchers.IO) {
                                            AppointmentRepository.cancel(appt)
                                            appointments.remove(appt)
                                        }
                                    },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colorScheme.error
                                    )
                                ) {
                                    Text("Cancelar consulta")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
