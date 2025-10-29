package com.example.clinica.data

import com.example.clinica.model.Appointment

object AppointmentRepository {

    /** Cria/guarda um agendamento em memória */
    fun add(appointment: Appointment) {
        FakeDatabase.appointments.add(appointment)
    }

    /** Lista agendamentos do paciente */
    fun byPatient(patientId: String): List<Appointment> =
        FakeDatabase.appointments.filter { it.patientId == patientId }

    /** Lista agendamentos do médico */
    fun byDoctor(doctorId: Int): List<Appointment> =
        FakeDatabase.appointments.filter { it.doctorId == doctorId.toString() }

    /** Cancela removendo exatamente o agendamento informado */
    fun cancel(appointment: Appointment): Boolean =
        FakeDatabase.appointments.remove(appointment)

    /**
     * Cancela por chave (útil quando você não tem a mesma instância do objeto).
     * Retorna true se removeu algum item.
     */
    fun cancelBy(
        patientId: String,
        doctorId: Int,
        date: String,
        time: String
    ): Boolean {
        val it = FakeDatabase.appointments.iterator()
        var removed = false
        while (it.hasNext()) {
            val a = it.next()
            if (
                a.patientId == patientId &&
                a.doctorId == doctorId.toString() &&
                a.date == date &&
                a.time == time
            ) {
                it.remove()
                removed = true
                break
            }
        }
        return removed
    }
}
