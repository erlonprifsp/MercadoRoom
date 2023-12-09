package br.ifsp.mercadoroom.data // pacote no qual a classe Produto está localizada -> define o namespace ou diretório em que a classe se encontra

import androidx.room.Entity // Importa a anotação Entity do Room, que é usada para marcar a classe como uma tabela dd banco de dados
import androidx.room.PrimaryKey // Importa a anotação PrimaryKey do Room, que é usada para definir a chave primária da entidade
import java.io.Serializable // Importa a interface Serializable do Java


// define as entidades que representarão as tabelas no banco de dados
@Entity // anotação usada para indicar que a classe Produto representa uma entidade do banco de dados
// quando uma classe é anotada com @Entity, o Room cria uma tabela correspondente no banco de dados com os campos especificados na classe
// classes de dados são usadas para manter dados
data class Produto ( // declaração de uma classe de dados (data class) chamada Produto, que representa um produto
    // chave primária do produto
    @PrimaryKey(autoGenerate = true) // anotação indica que a propriedade id é a chave primária da tabela e deve ser gerada automaticamente pelo Room (usando autoGenerate = true)
    val id: Int, // declaração da propriedade id como um valor imutável (usando val) do tipo Int
    // representa o nome do produto
    val nome:String, // declaração da propriedade nome como um valor imutável (usando val) do tipo String
    // representa a categoria do produto
    val categoria:String, // declaração da propriedade categoria como um valor imutável (usando val) do tipo String
    // representa a marca do produto, mas pode ser opcional (pode ser null)
    val marca:String? // declaração da propriedade marca como um valor imutável (usando val) do tipo String? que pode ser nulo (nullable)
): Serializable // permite que objetos da classe Produto sejam serializados
// convertidos em uma sequência de bytes que podem ser posteriormente desserializados para recriar o objeto
// para passar objetos entre atividades


// classe Produto é uma entidade do Room que será mapeada para uma tabela no banco de dados
// ela contém propriedades que representam colunas na tabela
// é anotada com as informações necessárias para que o Room saiba como criar e manipular essa tabela no banco de dados SQLite