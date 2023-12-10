package br.ifsp.mercadoroom.ui // declaração do pacote onde a classe ListaProdutosFragment está localizada

// importações das classes
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.ifsp.mercadoroom.R
import br.ifsp.mercadoroom.adapter.ProdutoAdapter
import br.ifsp.mercadoroom.data.Produto
import br.ifsp.mercadoroom.data.ProdutoDatabase
import br.ifsp.mercadoroom.databinding.FragmentListaProdutosBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// declaração da classe ListaProdutosFragment que estende a classe Fragment
class ListaProdutosFragment : Fragment(){ // representa um fragmento na UI do aplicativo

    private var _binding: FragmentListaProdutosBinding? = null // referência para o Binding do layout do fragmento

    private val binding get() = _binding!! // propriedade para acessar o Binding de forma segura


    lateinit var produtoAdapter: ProdutoAdapter

    // método para inflar e retornar o layout associado ao fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListaProdutosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_listaProdutosFragment_to_cadastroFragment) }

        return root
    }

    // método chamado imediatamente após onCreateView()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // lógica do fragmento é configurada aqui após a criação da view
        super.onViewCreated(view, savedInstanceState)

        // define um menu do tipo MenuHost
        val menuHost: MenuHost = requireActivity()

        // Define um provedor de menu no MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) { // método chamado para criar o menu
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu, menu) // infla um menu (R.menu.main_menu) utilizando o arquivo de menu main_menu.xml

                // configura um SearchView para filtrar os produtos
                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        produtoAdapter.filter.filter(p0)
                        return true
                    }

                })
            }

            // método chamado quando um item de menu é selecionado
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented") // não foi implementado nenhum comportamento
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    // método chamado quando o fragmento está visível para o usuário
    override fun onResume() {
        super.onResume()
        updateUI() // cChama o método updateUI() para atualizar a interface com a lista de produtos
    }

    // método que atualiza a interface com a lista de produtos
    // recuperada do banco de dados e exibida em um RecyclerView
    private fun updateUI()
    {
        // acessa o banco de dados utilizando coroutines
        val db = ProdutoDatabase.getDatabase(requireActivity().applicationContext) // obtém uma instância do banco de dados ProdutoDatabase usando o contexto da atividade atual (requireActivity().applicationContext)
        // obtêm a lista de produtos
        var produtosLista : ArrayList<Produto> // declara variável para armazenar a lista de produtos que será recuperada do banco de dados

        val recyclerView = binding.recyclerview // acessa o RecyclerView por meio do binding (vinculação de layout)

        // recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext()) // define um LinearLayoutManager como gerenciador de layout para o RecyclerView

        // inicia uma nova coroutine no contexto Dispatchers.IO para realizar operações assíncronas
        CoroutineScope(Dispatchers.IO).launch {
            // acessa o produtoDAO do banco de dados para buscar a lista de produtos
            produtosLista = db.produtoDAO().listarProdutos() as ArrayList<Produto> //  dados são acessados de maneira assíncrona dentro da coroutine
            // cria um Adapter
            produtoAdapter = ProdutoAdapter(produtosLista) // adaptador ProdutoAdapter usa a lista de produtos recuperada do banco de dados

            // volta para o contexto principal (UI thread) para atualizar a interface do usuário
            // após a conclusão da operação assíncrona
            withContext(Dispatchers.Main) {
                // configurar o adaptador ProdutoAdapter para exibir os dados na interface
                recyclerView.adapter = produtoAdapter // criado anteriormente no RecyclerView

                // define um OnClickListener para os itens da lista para navegar até a tela de detalhes ao clicar em um produto
                val listener = object : ProdutoAdapter.ProdutoListener { // cria um Listener para os itens do ProdutoAdapter

                    // responde ao clique em um item da lista
                    override fun onItemClick(pos: Int) {
                        val c = produtoAdapter.produtosListaFilterable[pos]

                        val bundle = Bundle()
                        bundle.putSerializable("produto", c)

                        findNavController().navigate(


                            R.id.action_listaProdutosFragment_to_detalheFragment,
                            bundle
                        )

                    }
                }
                produtoAdapter.setClickListener(listener) // define o Listener criado no Adapter para lidar com os cliques nos itens da lista
            }
        }

    }

}