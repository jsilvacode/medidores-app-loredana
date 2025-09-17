package com.loredana.medidores.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Lectura::class], version = 1, exportSchema = false)
@TypeConverters(Convertidores::class)
abstract class BaseDatosApp : RoomDatabase() {
    abstract fun lecturaDao(): LecturaDao

    companion object {
        @Volatile private var INSTANCE: BaseDatosApp? = null

        fun instancia(context: Context): BaseDatosApp =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BaseDatosApp::class.java,
                    "medidores.db"
                ).build().also { INSTANCE = it }
            }
    }
}
