package br.ifsp.mercadoroom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// define o banco de dados que utiliza as entidades e os DAOs

@Database(entities = [Produto::class], version = 1)
abstract class ProdutoDatabase: RoomDatabase() {
    abstract fun produtoDAO(): ProdutoDAO

    companion object {
        @Volatile
        private var INSTANCE: ProdutoDatabase? = null

        fun getDatabase(context: Context): ProdutoDatabase {
            return INSTANCE ?: synchronized(this) {
                // inicializa e obtêm uma instância do banco de dados
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProdutoDatabase::class.java,
                    "mercadoroom.db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}