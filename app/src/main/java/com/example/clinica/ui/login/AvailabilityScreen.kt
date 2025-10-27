package com.example.clinica.ui.login


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AvailabilityScreen() {
    val daysOfWeek = listOf(
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    )
    val selectedDays = remember { mutableStateListOf<String>() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Select your available days")
            Spacer(modifier = Modifier.height(16.dp))
            daysOfWeek.forEach { day ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .toggleable(
                            value = selectedDays.contains(day),
                            onValueChange = {
                                if (it) selectedDays.add(day) else selectedDays.remove(day)
                            }
                        )
                ) {
                    Checkbox(
                        checked = selectedDays.contains(day),
                        onCheckedChange = null // handled by toggleable
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(day)
                }
            }
        }
    }
}
