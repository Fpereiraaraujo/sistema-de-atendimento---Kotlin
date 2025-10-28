package com.example.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clinica.data.AppDatabase
import com.example.clinica.model.Doctor
import com.example.clinica.model.TimeSlotEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AvailabilityScreen(
    doctor: Doctor,
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var day by rememberSaveable { mutableStateOf("") }
    var startHour by rememberSaveable { mutableStateOf("") }
    var endHour by rememberSaveable { mutableStateOf("") }

    // Lista observável de horários
    val availability = remember { mutableStateListOf<TimeSlotEntity>() }

    // Carregar horários do banco ao abrir
    LaunchedEffect(doctor.id) {
        val db = AppDatabase.getDatabase(context)
        val slots = withContext(Dispatchers.IO) {
            db.timeSlotDao().getTimeSlotsByDoctor(doctor.id)
        }
        availability.clear()
        availability.addAll(slots)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Título
        Text(
            "Adicionar Horário",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Inputs
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = day,
                        onValueChange = { day = it },
                        label = { Text("Dia") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = startHour,
                        onValueChange = { startHour = it },
                        label = { Text("Início") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = endHour,
                        onValueChange = { endHour = it },
                        label = { Text("Fim") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Button(
                    onClick = {
                        if (day.isNotEmpty() && startHour.isNotEmpty() && endHour.isNotEmpty()) {
                            val slot = TimeSlotEntity(
                                doctorId = doctor.id,
                                day = day,
                                startHour = startHour,
                                endHour = endHour
                            )

                            // Salvar no banco e atualizar a UI
                            scope.launch {
                                val db = AppDatabase.getDatabase(context)
                                withContext(Dispatchers.IO) {
                                    db.timeSlotDao().insert(slot)
                                }
                                availability.add(slot)
                            }

                            day = ""
                            startHour = ""
                            endHour = ""
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Adicionar", fontSize = 16.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Horários Atuais",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Lista de horários
        if (availability.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Nenhum horário adicionado",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(availability) { slot ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = slot.day,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${slot.startHour} - ${slot.endHour}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
