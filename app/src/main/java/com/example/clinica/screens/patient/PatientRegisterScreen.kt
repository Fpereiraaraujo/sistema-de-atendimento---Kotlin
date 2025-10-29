package com.example.clinica.screens.patient

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientRegisterScreen(
    onRegistered: (patientId: String) -> Unit,
    onBack: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }

    // Dropdown de convênio
    val convenioOptions = listOf(
        "Unimed",
        "Amil",
        "Bradesco Saúde",
        "SulAmérica Saúde",
        "Hapvida NotreDame Intermédica",
        "Prevent Senior",
        "Porto Seguro Saúde",
        "Allianz Saúde",
        "Golden Cross",
        "São Francisco Saúde"
    )
    var convenioExpanded by remember { mutableStateOf(false) }
    var convenio by remember { mutableStateOf("") }

    var telefone by remember { mutableStateOf("") }

    var erro by remember { mutableStateOf<String?>(null) }
    var carregando by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Cadastro do Paciente",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
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

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    label = { Text("Senha") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = cpf,
                    onValueChange = { input -> cpf = input.filter { it.isDigit() }.take(11) },
                    label = { Text("CPF (apenas números)") },
                    singleLine = true,
                    supportingText = { if (cpf.isNotBlank()) Text("${cpf.length}/11") },
                    modifier = Modifier.fillMaxWidth()
                )

                // === Convênio (Dropdown) ===
                ExposedDropdownMenuBox(
                    expanded = convenioExpanded,
                    onExpandedChange = { convenioExpanded = !convenioExpanded }
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        readOnly = true,
                        value = convenio,
                        onValueChange = {},
                        label = { Text("Convênio") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = convenioExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = convenioExpanded,
                        onDismissRequest = { convenioExpanded = false }
                    ) {
                        convenioOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    convenio = opt
                                    convenioExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = telefone,
                    onValueChange = { input -> telefone = input.filter { it.isDigit() }.take(11) },
                    label = { Text("Telefone (apenas números)") },
                    singleLine = true,
                    supportingText = { if (telefone.isNotBlank()) Text("${telefone.length}/11") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (erro != null) {
                    Text(
                        text = erro!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Voltar") }

                    Button(
                        onClick = {
                            // Validações
                            if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                                erro = "Preencha nome, email e senha."
                                return@Button
                            }
                            if (convenio.isBlank()) {
                                erro = "Selecione um convênio."
                                return@Button
                            }
                            if (telefone.length < 10) {
                                erro = "Telefone inválido. Use DDD + número (10 ou 11 dígitos)."
                                return@Button
                            }
                            if (cpf.isNotBlank() && cpf.length != 11) {
                                erro = "CPF deve ter 11 dígitos (apenas números)."
                                return@Button
                            }

                            erro = null
                            carregando = true

                            // Integrar com repositório/Room aqui, se necessário.
                            val generatedId = "p" + Random.nextInt(100000, 999999)

                            carregando = false
                            onRegistered(generatedId)
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (carregando) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(22.dp)
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
