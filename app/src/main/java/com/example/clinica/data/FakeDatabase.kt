package com.example.clinica.data

object FakeDatabase {
    val doctors = listOf(
        Doctor(1, "Dra. Ana Souza", "Dermatologista", 4.8, listOf("10h", "11h", "15h")),
        Doctor(2, "Dr. Carlos Lima", "Cardiologista", 4.6, listOf("09h", "13h"))
    )
}
