package br.ifsp.mercadoroom // declaração do pacote no qual a classe MainActivity está localizada
// define o namespace ou diretório em que a classe se encontra

// importações das classes e componentes necessários para a funcionalidade da Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.ifsp.mercadoroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() { // declaração da classe MainActivity, que é uma subclasse da classe AppCompatActivity
    // AppCompatActivity é uma classe de suporte que permite usar recursos avançados das versões recentes do Android em versões mais antigas desse sistema operacional

    // declaração da variável appBarConfiguration do tipo AppBarConfiguration
    private lateinit var appBarConfiguration: AppBarConfiguration // esta variável é usada para configurar a barra de aplicativos com base na navegação

    // declaração da variável binding do tipo ActivityMainBinding
    private lateinit var binding: ActivityMainBinding // variável usada para acessar as views e recursos definidos no layout activity_main.xml

    // sobrescrita do método onCreate da classe AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) { // método é chamado quando a activity está sendo inicializada
        super.onCreate(savedInstanceState) // chama a implementação do método onCreate da classe pai AppCompatActivity
        binding = ActivityMainBinding.inflate(layoutInflater) // infla o layout activity_main.xml usando a classe ActivityMainBinding
        // ActivityMainBinding é uma classe gerada automaticamente pelo sistema de ligação de dados do Android (View Binding)
        // essa atribuição permite acessar as views do layout diretamente por meio da variável binding
        setContentView(binding.root) // define o layout inflado na variável binding como o layout da activity

        setSupportActionBar(binding.toolbar) // define a Toolbar do layout como a barra de suporte da activity permitindo a personalização da barra de ferramentas

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment // define o NavHostFragment (fragmento de navegação) no layout da atividade usando o supportFragmentManager
        val navController = navHostFragment.navController // obtém o controlador de navegação (NavController) do NavHostFragment
        // NavController gerencia a navegação entre diferentes destinos (fragmentos) da aplicação

        appBarConfiguration = AppBarConfiguration(navController.graph) // cria uma configuração da barra de aplicativos com base no grafo de navegação (NavGraph) associado ao NavController
        setupActionBarWithNavController(navController, appBarConfiguration) // configura a barra de aplicativos (ActionBar) para usar a navegação com base no NavController e na appBarConfiguration

    }

    // sobrescrita do método onSupportNavigateUp
    override fun onSupportNavigateUp(): Boolean { // chamado quando o botão "Up" da barra de aplicativos é pressionado
        // obtém o NavController associado ao NavHostFragment utilizando a função findNavController
        val navController = findNavController(R.id.fragmentContainerView) // útil para lidar com a navegação ao pressionar o botão "Up"

        return navController.navigateUp(appBarConfiguration) // trata a ação do botão "Up" na barra de aplicativos permitindo que o controlador de navegação (NavController) decida como lidar com essa ação com base na configuração da barra de aplicativos (appBarConfiguration)
                || super.onSupportNavigateUp() // retorna true se a navegação foi tratada com sucesso e false caso contrário
                // Se a navegação não for tratada, a superclasse AppCompatActivity será chamada para lidar com a ação padrão do botão "Up"
    }

}