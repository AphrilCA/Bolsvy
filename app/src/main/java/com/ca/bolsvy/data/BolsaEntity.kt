package com.ca.bolsvy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bolsas")
data class BolsaEntity(
    @PrimaryKey val id: Int,
    val nombreRes: String,   //"bolsa_snoopy_flores"
    val precio: Int,
    val medida: String,
    val imagenRes: String    //"bolsa_flores"
)
fun BolsaEntity.aBolsa(context: android.content.Context): Bolsa {
    val nombreId = context.resources.getIdentifier(nombreRes, "string", context.packageName)
    val imagenId = context.resources.getIdentifier(imagenRes, "drawable", context.packageName)
    return Bolsa(id, nombreId, precio, medida, imagenId)
}