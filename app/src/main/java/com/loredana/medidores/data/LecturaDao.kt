package com.loredana.medidores.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LecturaDao {
    @Query("SELECT * FROM lecturas ORDER BY fecha DESC, id DESC")
    fun obtenerTodas(): Flow<List<Lectura>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(lectura: Lectura)

    @Delete
    suspend fun eliminar(lectura: Lectura)

    @Query("DELETE FROM lecturas")
    suspend fun limpiar()
}
