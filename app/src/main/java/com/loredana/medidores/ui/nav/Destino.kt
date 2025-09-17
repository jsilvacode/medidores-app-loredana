package com.loredana.medidores.ui.nav
sealed class Destino(val route: String) {
    data object Listado : Destino("listado")
    data object Formulario : Destino("formulario")
}
