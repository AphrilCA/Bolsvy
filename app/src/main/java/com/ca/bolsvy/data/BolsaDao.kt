package com.ca.bolsvy.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BolsaDao {


    @Query("SELECT * FROM bolsas")
    fun obtenerBolsas(): Flow<List<BolsaEntity>>


    @Query("SELECT bolsaId FROM favoritos")
    fun obtenerIdsFavoritos(): Flow<List<Int>>


    @Query("SELECT bolsas.* FROM bolsas INNER JOIN favoritos ON bolsas.id = favoritos.bolsaId")
    fun obtenerBolsasFavoritas(): Flow<List<BolsaEntity>>

    @Query("SELECT COUNT(*) FROM bolsas")
    suspend fun contarBolsas(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarBolsas(bolsas: List<BolsaEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun agregarFavorito(favorito: FavoritoEntity)

    @Query("DELETE FROM favoritos WHERE bolsaId = :bolsaId")
    suspend fun quitarFavorito(bolsaId: Int)
}