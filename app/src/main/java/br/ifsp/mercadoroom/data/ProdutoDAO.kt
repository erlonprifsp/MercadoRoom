package br.ifsp.mercadoroom.data // declaração do pacote no qual a interface ProdutoDAO está localizada -> define o namespace ou diretório em que a interface se encontra

import androidx.room.Dao // importa a anotação Dao do Room
// importa diferentes anotações do Room usadas para operações de acesso a dados no banco de dados
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// DAO contém os métodos que acessam o banco de dados

@Dao // anotação usada para indicar que a interface ProdutoDAO é um Data Access Object (DAO)
interface ProdutoDAO { // interfaces marcadas com @Dao no Room contêm métodos que acessam e manipulam dados do banco de dados

    // aqui são definidas as consultas SQL

    // métodos na interface ProdutoDAO definem as operações de acesso ao banco de dados para a entidade Produto

    @Insert // anotação usada para indicar o método de inserção de dados no banco de dados
    fun inserirProduto(produto: Produto) // declaração do inserirProduto que usa a anotação @Insert
    // este método insere um objeto Produto no banco de dados

    // suspend fun inserirProduto(produto: Produto)
    // anotação @Insert do Room não é compatível com suspend
    // Room entende que métodos anotados com @Insert sejam métodos síncronos

    @Update // anotação usada para indicar o método de atualização de dados no banco de dados
    suspend fun atualizarProduto (produto: Produto) // declaração do método atualizarProduto que usa a anotação @Update
    // este método atualiza um objeto Produto no banco de dados
    // suspend indica que este método pode ser chamado de forma assíncrona

    @Delete // anotação usada para indicar o método de exclusão de dados no banco de dados
    suspend fun apagarProduto(produto: Produto) // declaração do método apagarProduto que usa a anotação @Delete
    // este método exclui um objeto Produto do banco de dados
    // suspend indica que este método pode ser chamado de forma assíncrona

    @Query("SELECT * FROM produto ORDER BY nome") // anotação usada para realizar consultas SQL personalizadas no banco de dados
    // SELECT * FROM produto ORDER BY nome -> consulta SQL que seleciona todos os produtos ordenados pelo nome
    suspend fun listarProdutos(): List<Produto> // declaração do método listarProdutos que usa a anotação @Query
    // suspend indica que este método pode ser chamado de forma assíncrona para retornar uma lista de objetos Produto
}