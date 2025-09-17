package com.loredana.medidores.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.loredana.medidores.R
import com.loredana.medidores.data.TipoMedidor
import com.loredana.medidores.ui.MedidoresViewModel
import com.loredana.medidores.ui.UiLectura
import com.loredana.medidores.ui.nav.Destino
import com.loredana.medidores.util.comoUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoScreen(
    nav: NavController,
    vm: MedidoresViewModel
) {
    val estado = vm.estadoListado.value

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(id = R.string.title_list)) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { nav.navigate(Destino.Formulario.route) }) {
                Text("+")
            }
        }
    ) { inner ->
        if (estado.items.isEmpty()) {
            Box(Modifier.padding(inner).padding(24.dp)) {
                Text(stringResource(id = R.string.empty_list))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(estado.items, key = { it.id }) { fila ->
                    FilaLectura(fila, onDelete = { vm.eliminar(fila) })
                }
            }
        }
    }
}

@Composable
private fun FilaLectura(item: UiLectura, onDelete: () -> Unit) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        ListItem(
            leadingContent = {
                val icono = when (item.tipo) {
                    TipoMedidor.AGUA -> R.drawable.ic_agua
                    TipoMedidor.LUZ -> R.drawable.ic_luz
                    TipoMedidor.GAS -> R.drawable.ic_gas
                }
                Icon(painterResource(icono), contentDescription = null)
            },
            headlineContent = {
                Text(
                    when (item.tipo) {
                        TipoMedidor.AGUA -> stringResource(R.string.type_agua)
                        TipoMedidor.LUZ -> stringResource(R.string.type_luz)
                        TipoMedidor.GAS -> stringResource(R.string.type_gas)
                    }
                )
            },
            supportingContent = {
                Text(stringResource(R.string.label_value_date, item.valor, item.fecha.comoUi()))
            },
            trailingContent = {
                Text(
                    text = stringResource(R.string.action_delete),
                    modifier = Modifier.clickable { onDelete() }
                )
            }
        )
    }
}
