package com.loredana.medidores

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loredana.medidores.ui.MedidoresViewModel
import com.loredana.medidores.ui.form.FormularioScreen
import com.loredana.medidores.ui.list.ListadoScreen
import com.loredana.medidores.ui.nav.Destino

@Composable
fun MedidoresApp(vm: MedidoresViewModel) {
    val nav = rememberNavController()
    MaterialTheme {
        Surface {
            NavHost(navController = nav, startDestination = Destino.Listado.route) {
                composable(Destino.Listado.route) { ListadoScreen(nav, vm) }
                composable(Destino.Formulario.route) { FormularioScreen(nav, vm) }
            }
        }
    }
}
