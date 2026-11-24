package com.ca.bolsvy.data

sealed class AppScreens(val route: String) {
    object Inicio : AppScreens("inicio")
    object Catalogo : AppScreens("catalogo")
    object Favoritos : AppScreens("favoritos")
    object Contacto : AppScreens("contacto")
    object Personalizacion : AppScreens("personalizacion")
    object Producto : AppScreens("producto/{id}") {
        fun crearRuta(id: Int) = "producto/$id"
    }
}