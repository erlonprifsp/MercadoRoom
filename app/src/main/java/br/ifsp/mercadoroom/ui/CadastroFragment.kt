package br.ifsp.mercadoroom.ui // declaração do pacote onde a classe CadastroFragment está localizada

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import br.ifsp.mercadoroom.R
import br.ifsp.mercadoroom.data.Produto
import br.ifsp.mercadoroom.data.ProdutoDatabase
import br.ifsp.mercadoroom.databinding.FragmentCadastroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// declaração da classe CadastroFragment que estende a classe Fragment
class CadastroFragment : Fragment(){ // esta classe representa um fragmento na UI do aplicativo

    // classe CadastroFragment é responsável pela captura de dados de entrada do usuário
    // criação de objetos Produto e inserção desses objetos no banco de dados usando o Room

    private var _binding: FragmentCadastroBinding? = null // referência para o Binding do layout do fragmento
    private val binding get() = _binding!! // binding é uma propriedade para acessar o Binding de forma segura

    // método para inflar e retornar o layout associado ao fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)

        return binding.root
    }

    // método chamado imediatamente após onCreateView()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // lógica do fragmento é configurada após a criação da view
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity() // obtém o MenuHost da atividade que é usado para adicionar um provedor de menu dinâmico

        // adiciona um provedor de menu à MenuHost
        menuHost.addMenuProvider(object : MenuProvider {

            // define o comportamento do menu e suas ações

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) { // método chamado para criar o menu
                // Add menu items here
                menuInflater.inflate(R.menu.cadastro_menu, menu) // arquivo de menu cadastro_menu.xml é inflado
            }

            // método chamado quando um item de menu é selecionado
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection

                // Aqui é feita a ação específica quando o item de menu "Salvar Produto" é selecionado
                return when (menuItem.itemId) {
                    R.id.action_salvarProduto -> {
                        // obtém os valores dos campos de texto do layout usando binding
                        val nome = binding.commonLayout.editTextNome.text.toString()
                        val categoria = binding.commonLayout.editTextCategoria.text.toString()
                        val marca = binding.commonLayout.editTextMarca.text.toString()

                        // cria um objeto Produto com os valores obtidos dos campos de entrada
                        val c = Produto( 0,nome, categoria, marca)

                        // acesso ao banco de dados usando Room
                        val db = ProdutoDatabase.getDatabase(requireActivity().applicationContext) // obtém uma instância do ProdutoDatabase

                        // insere o Produto no banco de dados usando coroutines e uma operação em thread separada (Dispatchers.IO)
                        CoroutineScope(Dispatchers.IO).launch {
                            db.produtoDAO().inserirProduto(c)
                        }

                        // depois da inserção, utiliza o findNavController().popBackStack() para voltar à tela anterior
                        // após a inserção bem-sucedida
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}
