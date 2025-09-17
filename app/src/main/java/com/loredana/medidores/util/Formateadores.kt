package com.loredana.medidores.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Formateadores {
    val fecha: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
}

fun LocalDate.comoUi(): String = this.format(Formateadores.fecha)
