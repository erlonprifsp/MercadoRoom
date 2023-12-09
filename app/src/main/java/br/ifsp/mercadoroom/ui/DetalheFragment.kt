package br.ifsp.mercadoroom.ui // declaração do pacote onde a classe DetalheFragment está localizada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import br.ifsp.mercadoroom.R
import br.ifsp.mercadoroom.data.Produto
import br.ifsp.mercadoroom.data.ProdutoDatabase
import br.ifsp.mercadoroom.databinding.FragmentDetalheBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// declaração da classe DetalheFragment que estende a classe Fragment
class DetalheFragment : Fragment() { // representa um fragmento na UI do aplicativo
    private var _binding: FragmentDetalheBinding? = null // referência para o Binding do layout do fragmento

    private val binding get() = _binding!! // propriedade para acessar o Binding de forma segura

    lateinit var produto: Produto

    // declaração da variável para o campos de texto nome
    lateinit  var nome: EditText
    // declaração da variável para o campos de texto categoria
    lateinit var categoria: EditText
    // declaração da variável para o campos de texto marca
    lateinit var marca: EditText

    // método para inflar e retornar o layout associado ao fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetalheBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    // método chamado imediatamente após onCreateView()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Aqui é onde a lógica do fragmento é configurada após a criação da view
        super.onViewCreated(view, savedInstanceState)

        // obtém o objeto Produto passado por argumento para o fragmento
        produto = requireArguments().getSerializable("produto",Produto::class.java) as Produto

        // define os valores dos campos de texto com base nos dados do Produto
        nome = binding.commonLayout.editTextNome
        categoria = binding.commonLayout.editTextCategoria
        marca = binding.commonLayout.editTextMarca

        // atribuindo valores aos campos de texto
        nome.setText(produto.nome)
        categoria.setText(produto.categoria)
        marca.setText(produto.marca)

        // Define um menu do tipo MenuHost
        val menuHost: MenuHost = requireActivity()

        // Define um provedor de menu no MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            // método chamado para criar o menu
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                // Infla um menu (R.menu.detalhe_menu)
                menuInflater.inflate(R.menu.detalhe_menu, menu) // o arquivo de menu detalhe_menu.xml é inflado
            }

            // define ações para os itens do menu

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean { // método chamado quando um item de menu é selecionado
                // Handle the menu selection
                return when (menuItem.itemId) {
                    // define ações para os itens do menu, como: atualizar ou excluir o Produto selecionado
                    R.id.action_alterarProduto -> {
                        // Acesso ao banco de dados usando Room
                        val db = ProdutoDatabase.getDatabase(requireActivity().applicationContext)

                        // Realiza operações de atualização no banco de dados usando coroutines e a classe ProdutoDatabase
                        val produtoUpdate=Produto(produto.id,nome.text.toString(),categoria.text.toString(),marca.text.toString())

                        CoroutineScope(Dispatchers.IO).launch {
                            db.produtoDAO().atualizarProduto(produtoUpdate)
                        }
                        // Após realizar as operações no banco de dados
                        // utiliza findNavController().popBackStack() para voltar à tela anterior
                        findNavController().popBackStack()
                        true
                    }
                    R.id.action_excluirProduto ->{

                        // Acesso ao banco de dados usando Room
                        val db = ProdutoDatabase.getDatabase(requireActivity().applicationContext)

                        // Realiza operações de exclusão no banco de dados usando coroutines e a classe ProdutoDatabase
                        CoroutineScope(Dispatchers.IO).launch {
                            db.produtoDAO().apagarProduto(produto)
                        }
                        // Após realizar as operações no banco de dados
                        // utiliza findNavController().popBackStack() para voltar à tela anterior
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}