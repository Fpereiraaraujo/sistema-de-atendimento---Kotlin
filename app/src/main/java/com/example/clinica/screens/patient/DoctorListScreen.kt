package com.example.clinica.screens.patient

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DoctorListScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("Voltar")
        }

        // (Conteúdo da lista pode ser adicionado depois)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Lista de médicos (em construção)",
            style = MaterialTheme.typography.titleMedium
        )
    }
}
