package com.example.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinica.data.AppDatabase
import com.example.clinica.model.Doctor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DoctorRegisterScreen(
    onRegisterSuccess: (Doctor) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var crm by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Cadastro do Médico",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Senha (sem KeyboardOptions)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = specialty,
                    onValueChange = { specialty = it },
                    label = { Text("Especialidade") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = crm,
                    onValueChange = { crm = it },
                    label = { Text("CRM") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Telefone sem KeyboardOptions
                OutlinedTextField(
                    value = phone,
                    onValueChange = { input ->
                        phone = input.filter { it.isDigit() } // aceita só números
                    },
                    label = { Text("Telefone") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição") },
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )

                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onBackClick,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Voltar")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (name.isBlank() || email.isBlank() || password.isBlank() || specialty.isBlank()) {
                                errorMessage = "Por favor, preencha todos os campos obrigatórios"
                                return@Button
                            }

                            isLoading = true
                            CoroutineScope(Dispatchers.IO).launch {
                                val newDoctor = Doctor(
                                    name = name,
                                    email = email,
                                    password = password,
                                    specialty = specialty,
                                    crm = crm,
                                    phone = phone,
                                    description = description
                                )
                                val id: Long = db.doctorDao().insert(newDoctor)
                                val savedDoctor = newDoctor.copy(id = id.toInt())
                                withContext(Dispatchers.Main) {
                                    isLoading = false
                                    onRegisterSuccess(savedDoctor)
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Registrar")
                        }
                    }
                }
            }
        }
    }
}
