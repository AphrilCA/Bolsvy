package com.ca.bolsvy.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ca.bolsvy.R

data class Bolsa(
    val id: Int,
    @StringRes val nombre: Int,
    val precio: Int,
    val medida: String,
    @DrawableRes val imagen: Int
)

val bolsasEjemplo = listOf(
    Bolsa(1, R.string.bolsa_snoopy_flores, 250, "40x40", R.drawable.bolsa_flores),
    Bolsa(2, R.string.bolsa_conejo, 120, "20x20", R.drawable.bolsa_conejo),
    Bolsa(3, R.string.bolsa_snoopy, 250, "40x40", R.drawable.bolsa_snoopy),
    Bolsa(4, R.string.bolsa_floreada, 250, "40x40", R.drawable.bolsa_floreada)
)