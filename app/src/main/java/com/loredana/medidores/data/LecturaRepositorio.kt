package com.loredana.medidores.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class LecturaRepositorio(private val dao: LecturaDao) {
    val lecturas: Flow<List<Lectura>> = dao.obtenerTodas()
    suspend fun agregar(tipo: TipoMedidor, valor: Double, fecha: LocalDate) {
        dao.insertar(Lectura(tipo = tipo, valor = valor, fecha = fecha))
    }
    suspend fun eliminar(lectura: Lectura) = dao.eliminar(lectura)
}
