package com.ca.bolsvy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [BolsaEntity::class, FavoritoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BolsvyDatabase : RoomDatabase() {

    abstract fun bolsaDao(): BolsaDao

    companion object {
        @Volatile
        private var instancia: BolsvyDatabase? = null

        fun obtener(context: Context): BolsvyDatabase {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    BolsvyDatabase::class.java,
                    "bolsvy.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Precarga las 4 bolsas la primera vez que se crea la base
                            instancia?.let { base ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    base.bolsaDao().insertarBolsas(bolsasIniciales)
                                }
                            }
                        }
                    })
                    .build()
                    .also { instancia = it }
            }
        }
    }
}

// Las 4 bolsas iniciales (nombres de recursos como texto)
val bolsasIniciales = listOf(
    BolsaEntity(1, "bolsa_snoopy_flores", 250, "40x40", "bolsa_flores"),
    BolsaEntity(2, "bolsa_conejo", 120, "20x20", "bolsa_conejo"),
    BolsaEntity(3, "bolsa_snoopy", 250, "40x40", "bolsa_snoopy"),
    BolsaEntity(4, "bolsa_floreada", 250, "40x40", "bolsa_floreada")
)