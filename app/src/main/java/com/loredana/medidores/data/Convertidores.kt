package com.loredana.medidores.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

class Convertidores {
    @TypeConverter
    fun desdeTipo(tipo: TipoMedidor): String = tipo.name

    @TypeConverter
    fun aTipo(raw: String): TipoMedidor = TipoMedidor.valueOf(raw)

    @TypeConverter
    fun desdeLocalDate(fecha: LocalDate): Long =
        fecha.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()

    @TypeConverter
    fun aLocalDate(millis: Long): LocalDate =
        Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
}
