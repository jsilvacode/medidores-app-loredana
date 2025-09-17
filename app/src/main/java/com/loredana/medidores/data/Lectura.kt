package com.loredana.medidores.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "lecturas")
data class Lectura(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tipo: TipoMedidor,
    val valor: Double,
    val fecha: LocalDate
)
