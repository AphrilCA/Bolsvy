package com.ca.bolsvy.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favoritos",
    foreignKeys = [
        ForeignKey(
            entity = BolsaEntity::class,
            parentColumns = ["id"],
            childColumns = ["bolsaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FavoritoEntity(
    @PrimaryKey val bolsaId: Int
)