package com.loredana.medidores.ui.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.loredana.medidores.R
import com.loredana.medidores.data.TipoMedidor
import com.loredana.medidores.ui.MedidoresViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    nav: NavController,
    vm: MedidoresViewModel
) {
    var tipo by remember { mutableStateOf(TipoMedidor.AGUA) }
    var valorTexto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf(LocalDate.now()) }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(id = R.string.title_form)) }) }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(stringResource(R.string.label_type))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = tipo == TipoMedidor.AGUA,
                    onClick = { tipo = TipoMedidor.AGUA },
                    label = { Text(stringResource(R.string.type_agua)) },
                    leadingIcon = { Icon(painterResource(R.drawable.ic_agua), null) }
                )
                FilterChip(
                    selected = tipo == TipoMedidor.LUZ,
                    onClick = { tipo = TipoMedidor.LUZ },
                    label = { Text(stringResource(R.string.type_luz)) },
                    leadingIcon = { Icon(painterResource(R.drawable.ic_luz), null) }
                )
                FilterChip(
                    selected = tipo == TipoMedidor.GAS,
                    onClick = { tipo = TipoMedidor.GAS },
                    label = { Text(stringResource(R.string.type_gas)) },
                    leadingIcon = { Icon(painterResource(R.drawable.ic_gas), null) }
                )
            }

            // Nota: omitimos KeyboardOptions para evitar dependencia; el teclado numérico es opcional para el build.
            OutlinedTextField(
                value = valorTexto,
                onValueChange = { valorTexto = it },
                label = { Text(stringResource(R.string.label_value)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            val datePickerState = rememberDatePickerState(initialSelectedDateMillis = java.util.Date().time)
            DatePicker(state = datePickerState)
            LaunchedEffect(datePickerState.selectedDateMillis) {
                datePickerState.selectedDateMillis?.let { millis ->
                    fecha = java.time.Instant.ofEpochMilli(millis)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate()
                }
            }

            if (error != null) Text(text = error!!, color = MaterialTheme.colorScheme.error)

            Button(
                onClick = {
                    val valor = valorTexto.toDoubleOrNull()
                    if (valor == null || valor <= 0.0) {
                        error = stringResource(R.string.error_value)
                        return@Button
                    }
                    error = null
                    vm.agregar(tipo, valor, fecha) { nav.navigateUp() }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.action_save)) }
        }
    }
}
