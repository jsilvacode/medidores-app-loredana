package com.loredana.medidores.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.loredana.medidores.data.BaseDatosApp
import com.loredana.medidores.data.TipoMedidor
import com.loredana.medidores.data.Lectura
import com.loredana.medidores.data.LecturaRepositorio
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

data class UiLectura(
    val id: Long,
    val tipo: TipoMedidor,
    val valor: Double,
    val fecha: LocalDate
)

data class EstadoListado(
    val items: List<UiLectura> = emptyList()
)

class MedidoresViewModel(app: Application) : AndroidViewModel(app) {
    private val repo: LecturaRepositorio

    init {
        val db = BaseDatosApp.instancia(app)
        repo = LecturaRepositorio(db.lecturaDao())
    }

    val estadoListado: StateFlow<EstadoListado> =
        repo.lecturas
            .map { lista -> EstadoListado(lista.map { UiLectura(it.id, it.tipo, it.valor, it.fecha) }) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EstadoListado())

    fun agregar(tipo: TipoMedidor, valor: Double, fecha: LocalDate, alTerminar: () -> Unit) {
        viewModelScope.launch {
            repo.agregar(tipo, valor, fecha)
            alTerminar()
        }
    }

    fun eliminar(item: UiLectura) {
        viewModelScope.launch {
            val entidad = Lectura(id = item.id, tipo = item.tipo, valor = item.valor, fecha = item.fecha)
            repo.eliminar(entidad)
        }
    }
}
