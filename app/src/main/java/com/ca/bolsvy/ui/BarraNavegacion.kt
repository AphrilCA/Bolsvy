package com.ca.bolsvy.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ca.bolsvy.R
import com.ca.bolsvy.data.AppScreens

private data class ItemNavegacion(
    val ruta: String,
    @StringRes val etiqueta: Int,
    val icono: ImageVector,
    val iconoSeleccionado: ImageVector
)

private val itemsNavegacion = listOf(
    ItemNavegacion(
        AppScreens.Inicio.route, R.string.nav_inicio,
        Icons.Outlined.Star, Icons.Filled.Star
    ),
    ItemNavegacion(
        AppScreens.Catalogo.route, R.string.nav_catalogo,
        Icons.Outlined.Search, Icons.Filled.Search
    ),
    ItemNavegacion(
        AppScreens.Favoritos.route, R.string.nav_favoritos,
        Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite
    ),
    ItemNavegacion(
        AppScreens.Contacto.route, R.string.nav_contacto,
        Icons.Outlined.Person, Icons.Filled.Person
    )
)

@Composable
fun BarraNavegacion(navController: NavController, rutaActual: String?) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
        itemsNavegacion.forEach { item ->
            val seleccionado = rutaActual == item.ruta
            NavigationBarItem(
                selected = seleccionado,
                onClick = {
                    navController.navigate(item.ruta) {
                        popUpTo(AppScreens.Inicio.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (seleccionado) item.iconoSeleccionado else item.icono,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.etiqueta),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}