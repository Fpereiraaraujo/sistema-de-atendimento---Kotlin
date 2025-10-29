package com.example.clinica.screens.patient

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.clinica.data.AppDatabase
import com.example.clinica.data.AppointmentRepository
import com.example.clinica.model.Appointment
import com.example.clinica.model.Doctor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PatientHomeScreen(
    patientId: String,
    onLogout: () -> Unit,
    onSchedule: () -> Unit
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    // agendamentos do paciente
    val myAppointments = remember { mutableStateListOf<Appointment>() }

    // cache de médicos: id -> Doctor
    var doctorsMap by remember { mutableStateOf<Map<Int, Doctor>>(emptyMap()) }

    // carrega agendamentos + médicos
    LaunchedEffect(patientId) {
        myAppointments.clear()
        myAppointments.addAll(AppointmentRepository.byPatient(patientId))

        val db = AppDatabase.getDatabase(ctx)
        val all = withContext(Dispatchers.IO) { db.doctorDao().getAll() }
        doctorsMap = all.associateBy { it.id }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Meus exames/consultas", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onSchedule,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.large
            ) { Text("Agendar exame") }

            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.large
            ) { Text("Sair") }
        }

        Divider(Modifier.padding(vertical = 16.dp))

        if (myAppointments.isEmpty()) {
            Text("Você ainda não tem exames/consultas marcados.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(myAppointments) { appt ->
                    val docId = appt.doctorId.toIntOrNull()
                    val doctor = docId?.let { doctorsMap[it] }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Data: ${appt.date}", style = MaterialTheme.typography.titleMedium)
                            Text("Hora: ${appt.time}")

                            // Nome e especialidade do médico
                            Text("Médico: ${doctor?.name ?: "—"}")
                            Text("Especialidade: ${doctor?.specialty ?: "—"}")

                            Spacer(Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = {
                                    // cancelar e atualizar lista
                                    AppointmentRepository.cancel(appt)
                                    myAppointments.remove(appt)
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Cancelar exame")
                            }
                        }
                    }
                }
            }
        }
    }
}
