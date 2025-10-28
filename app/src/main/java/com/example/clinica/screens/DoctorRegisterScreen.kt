package com.example.clinica.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.clinica.data.DoctorDatabase
import com.example.clinica.model.Doctor

@Composable
fun DoctorRegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var crm by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Register Doctor", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = specialty,
            onValueChange = { specialty = it },
            label = { Text("Specialty") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = crm,
            onValueChange = { crm = it },
            label = { Text("CRM") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { onBackClick() }) {
                Text("Back")
            }

            Button(onClick = {
                if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && specialty.isNotBlank()) {
                    // Cria novo doctor e adiciona no banco local
                    val newDoctor = Doctor(
                        id = DoctorDatabase.nextId(),
                        name = name,
                        email = email,
                        password = password,
                        specialty = specialty,
                        crm = crm,
                        phone = phone,
                        description = "Experienced doctor"
                    )
                    DoctorDatabase.addDoctor(newDoctor)
                    // Callback para avisar que o registro foi feito
                    onRegisterSuccess()
                }
            }) {
                Text("Register")
            }
        }
    }
}
