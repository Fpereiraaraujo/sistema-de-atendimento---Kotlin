package com.example.clinica.screens.patient

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.clinica.data.AppDatabase
import com.example.clinica.data.AppointmentRepository
import com.example.clinica.model.Appointment
import com.example.clinica.model.Doctor
import com.example.clinica.model.TimeSlotEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun BookAppointmentScreen(
    patientId: String,
    patientName: String,
    onBooked: () -> Unit,
    onBack: () -> Unit          // <-- novo
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    var doctors by remember { mutableStateOf<List<Doctor>>(emptyList()) }
    var specialties by remember { mutableStateOf<List<String>>(emptyList()) }

    var selectedSpec by remember { mutableStateOf<String?>(null) }
    var filteredDoctors by remember { mutableStateOf<List<Doctor>>(emptyList()) }
    var selectedDoctor by remember { mutableStateOf<Doctor?>(null) }

    var slots by remember { mutableStateOf<List<TimeSlotEntity>>(emptyList()) }
    var selectedSlot by remember { mutableStateOf<TimeSlotEntity?>(null) }

    var msg by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val db = AppDatabase.getDatabase(ctx)
        doctors = withContext(Dispatchers.IO) { db.doctorDao().getAll() }
        specialties = doctors.map { it.specialty }.distinct().sorted()
    }

    LaunchedEffect(selectedSpec) {
        selectedDoctor = null
        selectedSlot = null
        slots = emptyList()
        filteredDoctors = if (selectedSpec.isNullOrBlank()) emptyList() else doctors.filter { it.specialty == selectedSpec }
    }

    LaunchedEffect(selectedDoctor) {
        selectedSlot = null
        if (selectedDoctor != null) {
            val db = AppDatabase.getDatabase(ctx)
            slots = withContext(Dispatchers.IO) { db.timeSlotDao().getTimeSlotsByDoctor(selectedDoctor!!.id) }
        } else slots = emptyList()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ------- Topo com "Voltar" -------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(onClick = onBack) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                Spacer(Modifier.width(6.dp))
                Text("Voltar")
            }
        }

        Text("Agendar exame/consulta", style = MaterialTheme.typography.titleLarge)

        // Especialidade
        ExposedDropdown(
            label = "Especialidade",
            options = specialties,
            value = selectedSpec ?: "",
            onSelect = { selectedSpec = it }
        )

        // Médico
        if (filteredDoctors.isNotEmpty()) {
            ExposedDropdown(
                label = "Médico",
                options = filteredDoctors.map { it.name },
                value = selectedDoctor?.name ?: "",
                onSelect = { name -> selectedDoctor = filteredDoctors.firstOrNull { it.name == name } }
            )
        }

        // Slots
        if (slots.isNotEmpty()) {
            ExposedDropdown(
                label = "Horário disponível",
                options = slots.map { "${it.day} • ${it.startHour} - ${it.endHour}" },
                value = selectedSlot?.let { "${it.day} • ${it.startHour} - ${it.endHour}" } ?: "",
                onSelect = { label -> selectedSlot = slots.first { "${it.day} • ${it.startHour} - ${it.endHour}" == label } }
            )
        }

        Button(
            onClick = {
                if (selectedDoctor == null) { msg = "Escolha o médico"; return@Button }
                if (selectedSlot == null) { msg = "Escolha o horário"; return@Button }

                AppointmentRepository.add(
                    Appointment(
                        doctorId = selectedDoctor!!.id.toString(),
                        patientId = patientId,
                        patientName = patientName,
                        date = selectedSlot!!.day,
                        time = selectedSlot!!.startHour,
                        status = "Booked"
                    )
                )
                msg = "Agendado com sucesso!"
                onBooked()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedDoctor != null && selectedSlot != null
        ) { Text("Confirmar agendamento") }

        if (msg != null) Text(msg!!, color = MaterialTheme.colorScheme.primary)

        // (Opcional) listar agendamentos do paciente
        val meus = remember { mutableStateListOf<Appointment>() }
        LaunchedEffect(patientId) { meus.clear(); meus.addAll(AppointmentRepository.byPatient(patientId)) }

        if (meus.isNotEmpty()) {
            Text("Meus agendamentos", style = MaterialTheme.typography.titleMedium)
            LazyColumn {
                items(meus) { a ->
                    Text("- ${a.date} ${a.time} • ${a.status}")
                }
            }
        }
    }
}

/** Pequeno helper para dropdown exposto do Material3 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExposedDropdown(
    label: String,
    options: List<String>,
    value: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { opt ->
                DropdownMenuItem(text = { Text(opt) }, onClick = { onSelect(opt); expanded = false })
            }
        }
    }
}
