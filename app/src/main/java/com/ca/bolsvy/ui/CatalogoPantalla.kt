package com.ca.bolsvy.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ca.bolsvy.R
import com.ca.bolsvy.data.AppScreens
import com.ca.bolsvy.data.Bolsa
import com.ca.bolsvy.ui.theme.BolsvyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoPantalla(
    navController: NavController,
    bolsas: List<Bolsa>,
    favoritos: List<Int>,
    onToggleFavorito: (Int) -> Unit
) {

    var busqueda by remember { mutableStateOf("") }
    var medidaSeleccionada by remember { mutableStateOf<String?>(null) }
    val resources = LocalResources.current

    val filtros = listOf<Pair<Int, String?>>(
        R.string.filtro_todos to null,
        R.string.filtro_40 to "40x40",
        R.string.filtro_20 to "20x20"
    )

    val bolsasFiltradas = bolsas.filter { bolsa ->
        resources.getString(bolsa.nombre).contains(busqueda, ignoreCase = true) &&
                (medidaSeleccionada == null || bolsa.medida == medidaSeleccionada)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.titulo_catalogo),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_regresar),
                            contentDescription = stringResource(R.string.desc_regresar),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Buscador
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.buscar_bolsa)) },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.tertiary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Chips de filtro
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                filtros.forEach { (texto, medida) ->
                    FilterChip(
                        selected = medidaSeleccionada == medida,
                        onClick = { medidaSeleccionada = medida },
                        label = { Text(stringResource(texto)) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        border = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Lista o mensaje vacío
            if (bolsasFiltradas.isEmpty()) {
                Text(
                    text = stringResource(R.string.sin_resultados),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(bolsasFiltradas, key = { it.id }) { bolsa ->
                        TarjetaBolsa(
                            bolsa = bolsa,
                            esFavorito = bolsa.id in favoritos,
                            onToggleFavorito = { onToggleFavorito(bolsa.id) },
                            onClick = {
                                navController.navigate(AppScreens.Producto.crearRuta(bolsa.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoPantallaPreview() {
    BolsvyTheme {
        CatalogoPantalla(
            navController = rememberNavController(),
            bolsas = emptyList(),
            favoritos = emptyList(),
            onToggleFavorito = {}
        )
    }
}