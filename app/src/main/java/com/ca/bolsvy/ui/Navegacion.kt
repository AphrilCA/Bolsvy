package com.ca.bolsvy.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ca.bolsvy.data.AppScreens
import com.ca.bolsvy.data.BolsvyDatabase
import com.ca.bolsvy.data.FavoritoEntity
import com.ca.bolsvy.data.aBolsa
import kotlinx.coroutines.launch

@Composable
fun AppNavegacion() {
    val navController = rememberNavController()
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    val context = LocalContext.current
    val dao = remember { BolsvyDatabase.obtener(context).bolsaDao() }
    val scope = rememberCoroutineScope()

    // Datos que vienen de la base de datos
    val bolsas by dao.obtenerBolsas().collectAsState(initial = emptyList())
    val favoritos by dao.obtenerIdsFavoritos().collectAsState(initial = emptyList())

    // Convierte las entidades a Bolsa (la clase que usan las pantallas)
    val listaBolsas = remember(bolsas) { bolsas.map { it.aBolsa(context) } }

    val alternarFavorito: (Int) -> Unit = { id ->
        scope.launch {
            if (id in favoritos) dao.quitarFavorito(id)
            else dao.agregarFavorito(FavoritoEntity(id))
        }
    }

    val rutasConBarra = listOf(
        AppScreens.Inicio.route,
        AppScreens.Catalogo.route,
        AppScreens.Favoritos.route,
        AppScreens.Contacto.route
    )

    Scaffold(
        bottomBar = {
            if (rutaActual in rutasConBarra) {
                BarraNavegacion(navController, rutaActual)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.Inicio.route,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable(route = AppScreens.Inicio.route) {
                InicioPantalla(navController)
            }
            composable(route = AppScreens.Catalogo.route) {
                CatalogoPantalla(navController, listaBolsas, favoritos, alternarFavorito)
            }
            composable(route = AppScreens.Favoritos.route) {
                FavoritosPantalla(navController, listaBolsas, favoritos, alternarFavorito)
            }
            composable(route = AppScreens.Contacto.route) {
                ContactoPantalla(navController)
            }
        }
    }
}