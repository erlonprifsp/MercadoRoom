package br.ifsp.mercadoroom.data // declaração do pacote no qual a classe ProdutoDatabase está localizada
// define o namespace ou diretório em que a classe se encontra

// importações de classes e anotações necessárias para a configuração e utilização do Room para criação e manipulação de banco de dados
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// define o banco de dados que utiliza as entidades e os DAOs

@Database(entities = [Produto::class], version = 1) // anotação que define a classe ProdutoDatabase como um banco de dados Room
// entities = [Produto::class] -> indica que a entidade Produto é utilizada neste banco de dados
// version = 1 -> define a versão do banco de dados (número deve ser incrementado sempre que houver alterações no esquema do banco de dados)
abstract class ProdutoDatabase: RoomDatabase() {
    abstract fun produtoDAO(): ProdutoDAO // método abstrato que retorna um objeto ProdutoDAO
    // este método é responsável por disponibilizar a instância do DAO ProdutoDAO para interações com o banco de dados
    // através dos métodos definidos na interface ProdutoDAO

    // a função abstrata produtoDAO não contém a implementação
    // a responsabilidade da implementação concreta é do Room a qual fornece uma instância válida do ProdutoDAO
    // a classe gerada automaticamente pelo Room incorpora toda a lógica necessária para fornecer o DAO apropriado
    //  para executar operações no banco de dados, como: inserções, atualizações, consultas e exclusões

    companion object { // define um objeto companion object que permite a criação de funções e propriedades estáticas associadas à classe ProdutoDatabase
        // método estático pode ser chamado diretamente a partir da classe, sem precisar criar uma instância (objeto) daquela classe
        // podem ser chamados diretamente através do nome da classe
        @Volatile // anotação que indica que o valor da variável INSTANCE pode ser alterado por diferentes threads em um ambiente concorrente
        private var INSTANCE: ProdutoDatabase? = null // declaração de uma variável privada que armazena a instância do banco de dados -> é inicializada como nula

        fun getDatabase(context: Context): ProdutoDatabase { // método estático (associado ao objeto companion) que retorna uma instância do banco de dados ProdutoDatabase
            // Usa o padrão Singleton para garantir que apenas uma instância do banco de dados seja criada durante o ciclo de vida do aplicativo
            // e fornece um ponto global de acesso a essa instância
            return INSTANCE ?: synchronized(this) { // Utiliza o operador Elvis (?:) para retornar a instância existente do banco de dados, se não for nula
                // caso seja nula, cria uma nova instância de banco de dados dentro de um bloco synchronized
                // esse bloco sincronizado garante que apenas uma thread por vez possa criar uma nova instância do banco de dados

                // bloco sincronizado

                // inicializa e obtêm uma instância do banco de dados
                val instance = Room.databaseBuilder( // método utilizado para criar o banco de dados
                    context.applicationContext, // recebe o contexto da aplicação
                    ProdutoDatabase::class.java, // recebe a classe do banco de dados
                    "mercadoroom.db" // nome do arquivo do banco de dados
                ).build() // método utilizado para construir o banco de dados
                INSTANCE = instance // atribui a instância do banco de dados recém-criada à variável INSTANCE
                instance // retorna a instância do banco de dados criada
            }

            // implementação garante que ao chamar ProdutoDatabase.getDatabase(context), o método retornará sempre a mesma instância do banco de dados se já existir
            // caso contrário, criará uma nova instância
            // isso mantém a consistência do Singleton, garantindo que apenas uma instância do banco de dados seja utilizada
        }
    }
}