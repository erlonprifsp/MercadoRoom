package br.ifsp.mercadoroom.adapter // declaração do pacote no qual a classe ProdutoAdapter está localizada

// importações das classes e componentes necessários para a funcionalidade do ProdutoAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView // RecyclerView
import br.ifsp.mercadoroom.data.Produto
import br.ifsp.mercadoroom.databinding.ProdutoCelulaBinding // data binding

// declaração da classe ProdutoAdapter que herda de RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() e implementa Filterable
class ProdutoAdapter(val produtosLista:ArrayList<Produto>): RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>(),
    Filterable { // classe é responsável por gerenciar a exibição de itens da lista de produtos em um RecyclerView e implementar filtros na lista

    var listener: ProdutoListener? = null // variável que mantém uma referência para o ouvinte de eventos de clique ProdutoListener

    var produtosListaFilterable = ArrayList<Produto>() // lista filtrável utilizada para exibição dos dados após a aplicação de filtros

    private lateinit var binding: ProdutoCelulaBinding // variável para armazenar a referência da classe gerada pelo View Binding para o layout da célula do produto

    // bloco init
    init {
        // inicializa a lista produtosListaFilterable com a lista de produtos passada no construtor
        this.produtosListaFilterable = produtosLista
    }

    // método para definir o ouvinte de eventos de clique
    fun setClickListener(listener: ProdutoListener)
    {
        this.listener = listener
    }

    // método obrigatório a ser implementado de RecyclerView.Adapter
    override fun onCreateViewHolder( // responsável por inflar a visualização do item da lista a ser exibido
        parent: ViewGroup,
        viewType: Int
    ): ProdutoViewHolder {

        binding = ProdutoCelulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  ProdutoViewHolder(binding)
    }

    // método obrigatório a ser implementado de RecyclerView.Adapter
    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) { // responsável por associar os dados do Produto com a view correspondente
        holder.nomeVH.text = produtosListaFilterable[position].nome
        holder.categoriaVH.text = produtosListaFilterable[position].categoria
    }

    override fun getItemCount(): Int {
        return produtosListaFilterable.size
    }

    // classe interna que representa o ViewHolder de cada item na lista
    inner class ProdutoViewHolder(view: ProdutoCelulaBinding): RecyclerView.ViewHolder(view.root)
    {
        // mantém referências às views dentro da célula do produto
        val nomeVH = view.nome
        val categoriaVH = view.categoria

        // define ouvinte de clique
        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    interface ProdutoListener
    {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    produtosListaFilterable = produtosLista
                else
                {
                    val resultList = ArrayList<Produto>()
                    for (row in produtosLista)
                        if (row.nome.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    produtosListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = produtosListaFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                produtosListaFilterable = p1?.values as ArrayList<Produto>
                notifyDataSetChanged()
            }

        }
    }

}