package com.example.clinica.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(
    onDoctorClick: () -> Unit,
    onPatientClick: () -> Unit
) {
    // Fundo degradê moderno
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        MaterialTheme.colorScheme.background
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(32.dp)
        ) {
            Text(
                text = "Bem-vindo ao ClinicaApp",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            Text(
                text = "Quem é você?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Botão Médico
            Button(
                onClick = onDoctorClick,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Médico",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Paciente
            Button(
                onClick = onPatientClick,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Paciente",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}
