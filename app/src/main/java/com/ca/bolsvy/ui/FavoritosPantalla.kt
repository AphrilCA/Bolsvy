package com.ca.bolsvy.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ca.bolsvy.R
import com.ca.bolsvy.data.Bolsa
import com.ca.bolsvy.ui.theme.BolsvyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosPantalla(
    navController: NavController,
    bolsas: List<Bolsa>,
    favoritos: List<Int>,
    onToggleFavorito: (Int) -> Unit
) {
    val bolsasFavoritas = bolsas.filter { it.id in favoritos }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.titulo_favoritos),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        if (bolsasFavoritas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.favoritos_vacio),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bolsasFavoritas, key = { it.id }) { bolsa ->
                    TarjetaBolsa(
                        bolsa = bolsa,
                        esFavorito = true,
                        onToggleFavorito = { onToggleFavorito(bolsa.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritosPantallaPreview() {
    BolsvyTheme {
        FavoritosPantalla(
            navController = rememberNavController(),
            bolsas = emptyList(),
            favoritos = emptyList(),
            onToggleFavorito = {}
        )
    }
}