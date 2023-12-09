package br.ifsp.mercadoroom.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// DAO contém os métodos que acessam o banco de dados

@Dao
interface ProdutoDAO {

    // aqui são definidas as consultas SQL
    @Insert
    fun inserirProduto(produto: Produto)

    @Update
    suspend fun atualizarProduto (produto: Produto)

    @Delete
    suspend fun apagarProduto(produto: Produto)

    @Query("SELECT * FROM produto ORDER BY nome")
    suspend fun listarProdutos(): List<Produto>
}